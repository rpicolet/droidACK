/**
 * Control interface for the Android MVC framework
 * 
 * This interface ext, and forms 
 * the basis for Control components within the overall Android 
 * MVC framework by streamlining integration with Activities 
 * and Fragments.
 * 
 * An implementation is assumed to be an inner class of an 
 * Activity or Fragment.  The Activity  or Fragment should 
 * directly delegate to the like-named life-cycle methods 
 * specified below, which cover the necessary events.  
 * 
 * Note that the creation scenarios (and the related life-cycle
 * methods) for Activities and Fragments are different, so the 
 * enclosing Activity or Fragment should delegate to the 
 * appropriate methods as noted.
 * 
 * @author Randy Picolet
 */

/**
 * 
 *  using a pattern of delegating 
 * to .

 * and adds the ability to form a tree of 
 * Controls that integrate with Activities and Fragments to
 * simplify life-cycle support.  for their intended 
 * purposes of more flexible control, and integrates it 
 * with .
 *
 * An implementation is assumed to be an Inner Class of an 
 * Activity or Fragment, and is intended to integrate the
 * overall Android life-cycle (itself a well-defined FSM) 
 * with a Control-internal FSM that defines specific behaviors 
 * in the "active" state between onResume() and onPause() 
 * (which corresponds to the "RUNNING" OperationalState 
 * defined by IPauseableStateMachine). 
 *  
 * A standard composite pattern for the Controls themselves
 * is enabled by two abstract implementations; a concrete 
 * Control class should simply extend one of the two:
 * 	- AControlModule implements a "leaf" node 
 * 	- AFragmentHostManager implements a "container" node 
 * 		nesting one or more Fragment Controls 
 * In addition, state transitions are implemented
 * accordingly, using simple transitions for "leaf" Controls 
 * and FragmentTransactions for Composites. 
 * 
 * Both Activity or Fragment Controls can be either leaf
 * or composite, but only Fragments can actually be 
 * nested, so the result is always a tree with a single root 
 * Activity and any number of nested Fragments. The entire 
 * Control tree is then subject to the Android life-cycle, 
 * with each Control node delegating life-cycle calls to its 
 * own current ControlState.
 * 
 * @author Randy Picolet
 *
 */
package rpicolet.mvc;