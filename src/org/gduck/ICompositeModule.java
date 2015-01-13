//
//	Copyright (c) 2015,  Randy Picolet
//
//	This software is covered by the MIT license (see license.txt). 

package org.gduck;

/**
 * Specifies a generic composite of ComposableModules to enable recursive 
 * nesting of ControlModules within a RootModule
 * 
 * @author Randy Picolet
 */

public interface ICompositeModule 
		 extends IComposableModule<IComposableModule<?>> {
}
