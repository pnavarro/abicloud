/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in 
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is available at http://www.abiquo.com/.....
 * 
 * The Initial Developer of the Original Code is Soluciones Grid, S.L. (www.abiquo.com),
 * Consell de Cent 296, Principal 2º, 08007 Barcelona, Spain.
 * 
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License", 
 * available at http://cpal.abiquo.com/), in which case the 
 * provisions of CPAL License are applicable instead of those above. In relation 
 * of this portions of the Code, a Legal Notice according to Exhibits A and B of 
 * CPAL Licence should be provided in any distribution of the corresponding Code 
 * to Graphical User Interface.
 */
package com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance;


import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.virtualappliance.Node;
import com.abiquo.abiserver.pojo.virtualappliance.NodeVirtualImage;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

/**
 * 
 * @author Oliver
 *
 */
public class NodeVirtualImageHB extends NodeHB
{
	private static final long serialVersionUID = 2350380932461612409L;
	
	private VirtualmachineHB virtualMachineHB;
	private VirtualimageHB virtualImageHB;
	
	public NodeVirtualImageHB()
	{
		super();
		
		virtualMachineHB = null;
		virtualImageHB = null;
	}
	
	public NodeVirtualImageHB(NodeHB nodeHB)
	{
		this.idNode = nodeHB.idNode;
		this.idVirtualApp = nodeHB.idVirtualApp;
		this.name = nodeHB.name;
		this.nodeTypeHB = nodeHB.nodeTypeHB;
		this.posX = nodeHB.posX;
		this.posY = nodeHB.posY;
		
		this.virtualImageHB = null;
		this.virtualMachineHB = null;
	}
	
	public VirtualmachineHB getVirtualMachineHB() {
		return virtualMachineHB;
	}
	public void setVirtualMachineHB(VirtualmachineHB virtualMachineHB) {
		this.virtualMachineHB = virtualMachineHB;
	}
	public VirtualimageHB getVirtualImageHB() {
		return virtualImageHB;
	}
	public void setVirtualImageHB(VirtualimageHB virtualImageHB) {
		this.virtualImageHB = virtualImageHB;
	}
	
	public IPojo toPojo()
	{
		NodeVirtualImage nodeVirtualImage = new NodeVirtualImage((Node) super.toPojo());
		
		//Adding NodeVirtualImageHB's fields
		nodeVirtualImage.setVirtualImage((VirtualImage) this.virtualImageHB.toPojo());
		if(this.virtualMachineHB != null)
			nodeVirtualImage.setVirtualMachine((VirtualMachine) this.virtualMachineHB.toPojo());
		else
			nodeVirtualImage.setVirtualMachine(null);
		
		return nodeVirtualImage;
	}
	
}
