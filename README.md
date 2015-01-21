
# droid ActiveControls Kit

droid ActiveControls Kit (droidACK) is an Android library for apps with complex
UI behaviors. droidACK enables an MVC-like (Presentation Layer) architectural 
pattern named ActiveControl (AC), tailored to Android apps; use of the pattern 
is enabled by the Kit, and vice versa.
 
NOTE: this is an early "developer release" of a work-in-progress, intended
to solicit feedback and determine whether to pursue further development.

The AC pattern borrows much from prior art, but uses a different approach from 
the major MVC-based patterns. For an excellent summary of the key historical 
MVC variants, see Derek Greer's article at: 

[link]http://aspiringcraftsman.com/2007/08/25/interactive-application-architecture

The overall droidACK framework (AC pattern plus Kit proper) has three primary 
goals:
- Clear separation of concerns for application compoments
- Flexible Control structures for mapping to/between Models and Views 
- Integrated, explicit management (and tracing) of User and System events
The following sections elaborate these goals and provide an overview of 
the framework implementation.

##Separation of Concerns

AC Model components are responsible for managing the app data (session state) 
and its persistence (record state), including any content provider/back-end 
synchronization. AC minimizes Model coupling to the requirement that 
all Model classes implement an enhanced Observer pattern with configurable 
notifications granularity and commits. droidACK specifies this requirement with 
the `IModel` interface and the `AModel` implementation that can be easily 
extended by Model classes. If extension is not feasible (for example, due to 
conflicts with the selected storage technology), then the Model classes must 
implement `IModel` by some other means.

AC View components are responsible for rendering graphic outputs and detecting 
user inputs, which means droidACK simply uses the existing Android `View` class 
hierarchy. This choice directly enables complete View independence via the 
various listener interfaces and avoids any `View` extensions/wrappers. This 
also means both AC Models and Views are "passive" observed event sources with 
no knowledge of each other, and it is left to AC Controls to observe and 
react to both, and provide the appropriate app-specific logic.

AC Control components are responsible for everything else, including:
- handling system/context events
- managing View-only state and navigation, and 
- 2-way synchronization of Model instances with their Views. 
The bulk of the droidACK library is focused on providing these functions.

##Control Structures

AC organizes Controls as a tree of Composite-pattern ControlModule containers 
that map to one or more Views or View containers, and defines two specific
variants of the generic base ControlModule:
- A MediatorModule manages 2-way synchronization between a Model instance and 
  ony number of Views and/or other Controls, and may nest other MediatorModules 
  to reflect the Model instance structure; such nested MediatorModules are
  automatically synchronized when any ancestor selection changes.
- A ViewStateModule provides a formal state machine for control of more complex 
  View behaviors.
Most use case/control logic can (and should) be directly implemented using 
appropriate cooperation and communication between these basic ControlModule
types.

This AC Control structure is refined and implemented by droidACK to support 
the Android environment. Notably, an `Activity` and its (nested) `Fragment`s 
naturally form the overall Control tree, so the Kit provides `IRootModule` to 
enable the AC framework within an `Activity` or `Fragment`, and to support 
control flows between them.

##Event Management

The AC/droidACK framework recognizes four sources/types of events, and enables 
integrated handling of those events, as follows:

1. *Context events*; these are the platform/OS events that an app may need to 
   handle. The base AC Control is the natural location for declaring context 
   event handlers, as ControlModules can then easily propagate these events to 
   every child Control. 
   
   In droidACK, the context events are the `Activity` and `Fragment` lifecycle 
   events corresponding to `onCreate`, `onCreateView`, `onResume`, and so on. 
   `IControl` specifies the event handlers, and `AControl` provides their
   implementations, to be extended as needed. Each `Activity` and `Fragment` 
   is responsible for delegating the events to its `IRootModule`;
   `AControlActivity` and `AControlFragment` provide this functionality as a
   convenience.

2. *Model-change notifications to MediatorModules*; as noted previously,
   these events are handled in AC by the protocol between Model and 
   MediatorModule. droidACK implements this protocol via `IModel` and 
   `IMediatorModule` and their abstract implementations. The events themselves 
   are published as `Property` enums to which `IMediatorModules` can subscribe 
   individually. A `Property` refers to a convenient set of `IModel` state 
   changes, including instance selections, which are propagated to all 
   child `IModuleMediators` to coarsely sync `View` updates for all dependent 
   `IModel` instances.

3. *View input events to MediatorModules*; as mentioned earlier, these events 
   are delivered via the various standard `View` listener interfaces 
   (`onClick`, for instance), as implemented by the `IMediatorModule` sub-type 
   for the generating `View`. Other than assigning this responsibility to 
   `IMediatorModules`, no further mechanisms (late binding, for example) are 
   currently provided. 

4. *ViewStateModule events*; as a formal state machine, an AC ViewStateModule 
   must publish the events to which it can react; droidACK uses a simple
   enumeration for this. The state machine mechanics are specified/implemented
   by `IViewStateModule`/`AViewStateModule`; concrete implementations define
   the events and states for their particular state diagrams. The state 
   interfaces and behaviors expected by `AViewStateModule`are provided by 
   `AViewState`; concrete implementations provide the state-specific event 
   handling, entry, and exit methods. The state diagram is implemented 
   exclusively between the`onResume` and `onPause` lifecycle events; the 
   current ViewState is automatically saved and restored.

##Current Status

The AC/droidACK framework has been used to develop a complex, real-world 
2-player 3D-interactive game app (and the app has likewise driven the 
framework). That's not a lot of experience, nonetheless it has been successful 
in achieving the above stated goals. Key areas needing further exploration and
effort include:
- Integration of/with Adapters, navigational widgets (tabs, for example), and 
  standard navigational events (back, up, and such)
- Use of late binding and/or dependecy injection for increased decoupling 
  between Models, Views, and Controls 
- Migration to Android Studio
- Documentation and examples for the current state of affairs. 

Finally, given its strong separation of Models and Views, the AC/droidACK
framework seems to be a good candidate for automated testing. 

Copyright (c) 2015,  Randy Picolet
Published under the MIT license (see license.txt). 
