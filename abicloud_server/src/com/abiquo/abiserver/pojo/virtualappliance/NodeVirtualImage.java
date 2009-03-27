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
 * Consell de Cent 296, Principal 2�, 08007 Barcelona, Spain.
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
package com.abiquo.abiserver.pojo.virtualappliance;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeVirtualImageHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

/**
 * @author Oliver
 */
public class NodeVirtualImage extends Node
{
    private VirtualImage virtualImage;

    private VirtualMachine virtualMachine;

    public NodeVirtualImage()
    {
        super();
        virtualImage = null;
        virtualMachine = null;
    }

    public NodeVirtualImage(Node node)
    {
        this.id = node.id;
        this.idVirtualAppliance = node.idVirtualAppliance;
        this.modified = node.modified;
        this.name = node.name;
        this.nodeType = node.nodeType;
        this.posX = node.posX;
        this.posY = node.posY;

        this.virtualImage = null;
        this.virtualMachine = null;
    }

    public VirtualImage getVirtualImage()
    {
        return virtualImage;
    }

    public void setVirtualImage(VirtualImage virtualImage)
    {
        this.virtualImage = virtualImage;
    }

    public VirtualMachine getVirtualMachine()
    {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine)
    {
        this.virtualMachine = virtualMachine;
    }

    public IPojoHB toPojoHB()
    {
        NodeVirtualImageHB nodeVirtualImageHB = new NodeVirtualImageHB((NodeHB) super.toPojoHB());

        // Adding NodeVirtualImage's fields
        nodeVirtualImageHB.setVirtualImageHB((VirtualimageHB) this.virtualImage.toPojoHB());
        if (this.virtualMachine != null)
            nodeVirtualImageHB.setVirtualMachineHB((VirtualmachineHB) this.virtualMachine
                .toPojoHB());
        else
            nodeVirtualImageHB.setVirtualMachineHB(null);

        return nodeVirtualImageHB;
    }

}
