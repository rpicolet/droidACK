//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

/**
 * droid Active Control Kit (droidACK) is a library supporting an MVC-like 
 * architectural pattern (also named droidAck) tailored to Android apps; use 
 * of the pattern is enabled by the Kit, and vice versa.
 * <p> 
 * NOTE: this is an early "developer release" of a work-in-progress.
 * <p>
 * The droidACK pattern borrows much from prior art, so may seem highly similar
 * to other MVC-based patterns, but it is not exactly like any of them. For 
 * an excellent discussion of the major MVC variants, see Derek Greer's 
 * article at: 
 * {@literal http://aspiringcraftsman.com/2007/08/25/interactive-application-architecture}
 * <p>
 * The overall droidACK framework (pattern plus Kit proper) has three primary 
 * goals (elaborated below):
 * - Clear separation of concerns for application compoments
 * - Flexible Control structures for mapping to/between Models and Views 
 * - Integrated, explicit management of User and System events
 * <p>
 * 		Separation of Concerns
 * <p>
 * droidACK Model components are responsible for managing the domain data (session 
 * state) and its persistence (record state). droidACK minimizes Model coupling to 
 * the requirement that all Models implement the IModel interface to support 
 * the Observer pattern (with configurable granularity) and commits. AModel 
 * provides an implementation that can be easily extended by Model classes. If 
 * extension is not feasible (for example, due to conflicts with the selected 
 * storage technology), then the Model classes must implement IModel by some 
 * other means.
 * <p>
 * droidACK View components are responsible for rendering graphic outputs and 
 * detecting user inputs, which is to say droidACK simply uses the existing 
 * Android View class hierarchy. This choice directly enables complete View 
 * independence via the various Listener interfaces and avoids any View 
 * extensions. This also means both Models and Views are observed event 
 * sources, and it is left to droidACK Controls to observe and react to both as 
 * needed.
 * <p>
 * droidACK Control components are responsible for handling system lifecycle 
 * events, managing View-only state and navigation, and 2-way synchronization 
 * of Model instances and properties with their Views. The bulk of the droidACK
 * library is focused on providing these functions. Notably, IControl 
 * (the base type for all Controls) enforces/enables handling of lifecycle 
 * events and associate every Control with its View, ICompositeModule provides 
 * a Composite-pattern container for related Controls, and IMediatorModule manages
 * 2-way synchronization between a Model instance and one or more Views.
 * <p>
 *		Control Structures
 * <p>
 * An Android Activity and its (nested) Fragments form an overall tree that 
 * reflects the corresponding View tree and Fragment sub-trees. droidACK IManagers 
 * form a logical tree of Controls within an Activity or Fragment, with an 
 * IFragmentControlModule as the root.   
 * <p>
 * Controls logically depend on their observed Models and/or Views, including 
 * the instance relationships. In general, interactive apps present views of a 
 * user-selected subset of the model instances in response to user inputs, but 
 * the relationship structures differ between the current model selections and 
 * its views. droidACK simplifies mapping between of Controls to different Model and View instance 
 * structures via several mechanisms:
 * composite pattern
 * mediator per instance 
 * use of composite Mediator
 * Controls which implement the Observer role relative to Model instances and
 * . s
 * by first deeming 
 * an Activity or Fragment to be the logical container for any Controls 
 * mapped to the Views in the tree defined by its root ViewGroup. Within 
 * each Activity or Fragment,  
 * through a 
 * flexible use of the Composite pattern.   
 * <p>
 *		Integrated Event Management
 * - 
 * - Integrated management of user and system events via finite state machines, 
 * 		including the App, Activity, and Fragment life-cycles
 * 
 * @author Randy Picolet
 * 
 */
package org.droidack;