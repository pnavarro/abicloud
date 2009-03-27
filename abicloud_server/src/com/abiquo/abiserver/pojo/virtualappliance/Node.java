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
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeTypeHB;
import com.abiquo.abiserver.pojo.IPojo;

/**
 * This class represents a Virtual Appliance's node Relations between nodes are from a child to its
 * parent
 * 
 * @author Oliver
 */

public class Node implements IPojo
{
    /* ------------- Public constants ------------- */
    public static final int NODE_NOT_MODIFIED = 0;

    public static final int NODE_MODIFIED = 1;

    public static final int NODE_ERASED = 2;

    public static final int NODE_NEW = 3;

    /* ------------- Public attributes ------------- */
    protected int id;

    protected String name;

    protected int idVirtualAppliance;

    protected NodeType nodeType;

    // For drawing purposes
    protected int posX;

    protected int posY;

    // For performance purposes
    // To be set when a node has been modified, when we want to save changes on editing a virtual
    // appliance
    protected int modified;

    /* ------------- Constructor ------------- */
    public Node()
    {
        id = 0;
        name = "";
        idVirtualAppliance = 0;
        nodeType = new NodeType();

        posX = 0;
        posY = 0;

        modified = NODE_NOT_MODIFIED;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIdVirtualAppliance()
    {
        return idVirtualAppliance;
    }

    public void setIdVirtualAppliance(int idVirtualAppliance)
    {
        this.idVirtualAppliance = idVirtualAppliance;
    }

    public NodeType getNodeType()
    {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType)
    {
        this.nodeType = nodeType;
    }

    public int getPosX()
    {
        return posX;
    }

    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    public int getPosY()
    {
        return posY;
    }

    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    public int getModified()
    {
        return modified;
    }

    public void setModified(int modified)
    {
        this.modified = modified;
    }

    public IPojoHB toPojoHB()
    {
        NodeHB nodeHB = new NodeHB();

        nodeHB.setIdNode(this.id);
        nodeHB.setIdVirtualApp(this.idVirtualAppliance);
        nodeHB.setName(this.name);
        nodeHB.setNodeTypeHB((NodeTypeHB) this.nodeType.toPojoHB());
        nodeHB.setPosX(this.posX);
        nodeHB.setPosY(this.posY);

        return nodeHB;
    }
}
