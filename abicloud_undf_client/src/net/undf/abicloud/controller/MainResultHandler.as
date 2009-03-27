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

package net.undf.abicloud.controller
{
	import mx.resources.ResourceBundle;
	
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.vo.main.MainResult;
	import net.undf.abicloud.vo.result.BasicResult;
	import net.undf.abicloud.vo.result.DataResult;
	
	/**
     * Class to handle server responses when calling infrastructure remote services defined in InfrastructureEventMap
 	 **/
	public class MainResultHandler extends ResultHandler
	{
		
		
		/* ------------- Constructor --------------- */
		public function MainResultHandler()
		{
			super();
		}
		
		[ResourceBundle("Common")]
		private var rb:ResourceBundle;
		
		
		
		public function handleGetCommonInformation(result:BasicResult):void
		{
			if(result.success)
			{
				var mainResult:MainResult = DataResult(result).data as MainResult;
				
				//Setting the common information
				AbiCloudModel.getInstance().operatingSystems = mainResult.operatingSystems;
				AbiCloudModel.getInstance().userManager.roles = mainResult.roles;
				AbiCloudModel.getInstance().infrastructureManager.hypervisorTypes = mainResult.hypervisorTypes;
				
			}
			else
			{
				//There was a problem retrieving the common information
				super.handleResult(result);
			}
		}
	}
}