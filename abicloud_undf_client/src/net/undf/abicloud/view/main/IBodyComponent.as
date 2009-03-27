/*
* The contents of this file are subject to the Common Public Attribution License

* Version 1.0 (the "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at http://cpal.abiquo.com.



* The License is based on the Mozilla Public License Version 1.1 but Sections 14
* and 15 have been added to cover use of software over a computer network and
* provide for limited attribution for the Original Developer. In addition,



* Exhibit A has been modified to be consistent with Exhibit B.
*
* Software distributed under the License is distributed on an "AS IS" basis,
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License



* for the specific language governing rights and limitations under the License.
*
* The Original Code is abiquo,  14 Jul 2008. The Original Developer is the
* Initial Developer. The Initial Developer of the Original Code is Soluciones Grid,



* S.L.. All portions of the code are Copyright © Soluciones Grid, S.L.
* All Rights Reserved.
*/

package net.undf.abicloud.view.main
{
	/**
	 * Interface to be implemented by any component inside the Main.mxml body viewstack
	 * @author Oliver
	 * 
	 */	
	public interface IBodyComponent
	{
		/**
		 * Indicates if the IBodyComponent has unsaved changes
		 * 
		 * This method is usually called when switching between different IBodyComponents.
		 * When going from an IBodyComponent to another, changes made in the previous IBodyComponent
		 * may be lost 
		 * @return True if the IBodyComponent has unsaved changes, that may be saved before exiting from
		 * the IBodyComponent
		 * 
		 */		
		function hasUnsavedChanges():Boolean;
		
		/**
		 * Saves all unsaved changes that this IBodyComponent has
		 * This function will only be called if hasUnsavedChanges method returns true,
		 * and before the IBodyComponent is hidden
		 */
		function saveChanges():void;
		
		/**
		 * This function will be called when user wants to discard the current unsaved changes
		 * The component that implement this function must no longer inform that has unsaved changes,
		 * until new changes are made
		 */
		function discardUnsavedChanges():void;
	}
}