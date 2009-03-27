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
package com.abiquo.abiserver.pojo.virtualimage;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.SoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.CategoryHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.IconHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.RepositoryHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageTypeHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.SO;

public class VirtualImage implements IPojo
{

    /* ------------- Public atributes ------------- */
    private int id;

    private String name;

    private String description;

    private String path;

    private SO so;

    private long hdRequired;

    private int ramRequired;

    private int cpuRequired;

    private Category category;

    private Repository repository;

    private Icon icon;

    private Boolean deleted;

    private VirtualImageType virtualImageType;

    /* ------------- Constructor ------------- */
    public VirtualImage()
    {
        id = 0;
        name = "";
        description = "";
        path = "";
        so = new SO();
        hdRequired = 0;
        ramRequired = 0;
        cpuRequired = 0;
        category = new Category();
        repository = new Repository();
        icon = new Icon();
        deleted = false;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public SO getSo()
    {
        return so;
    }

    public void setSo(SO so)
    {
        this.so = so;
    }

    public long getHdRequired()
    {
        return hdRequired;
    }

    public void setHdRequired(long hdRequired)
    {
        this.hdRequired = hdRequired;
    }

    public int getRamRequired()
    {
        return ramRequired;
    }

    public void setRamRequired(int ramRequired)
    {
        this.ramRequired = ramRequired;
    }

    public int getCpuRequired()
    {
        return cpuRequired;
    }

    public void setCpuRequired(int cpuRequired)
    {
        this.cpuRequired = cpuRequired;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Repository getRepository()
    {
        return repository;
    }

    public void setRepository(Repository repository)
    {
        this.repository = repository;
    }

    public Icon getIcon()
    {
        return icon;
    }

    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    public VirtualImageType getVirtualImageType()
    {
        return virtualImageType;
    }

    public void setVirtualImageType(VirtualImageType virtualImageType)
    {
        this.virtualImageType = virtualImageType;
    }

    public IPojoHB toPojoHB()
    {
        VirtualimageHB virtualImageHB = new VirtualimageHB();

        virtualImageHB.setIdImage(this.id);
        virtualImageHB.setRepository((RepositoryHB) this.repository.toPojoHB());
        virtualImageHB.setSo((SoHB) this.so.toPojoHB());
        if (this.icon != null)
            virtualImageHB.setIcon((IconHB) this.icon.toPojoHB());
        else
            virtualImageHB.setIcon(null);
        virtualImageHB.setCategory((CategoryHB) this.category.toPojoHB());
        virtualImageHB.setName(this.name);
        virtualImageHB.setDescription(this.description);
        virtualImageHB.setPathName(this.path);
        virtualImageHB.setHdRequired(this.hdRequired);
        virtualImageHB.setRamRequired(this.ramRequired);
        virtualImageHB.setCpuRequired(this.cpuRequired);
        virtualImageHB.setDeleted(this.deleted ? 1 : 0);
        virtualImageHB.setVirtualimageTypeHB((VirtualimageTypeHB) this.virtualImageType.toPojoHB());

        return virtualImageHB;
    }

}
