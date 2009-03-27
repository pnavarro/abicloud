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

import java.util.ArrayList;

/**
 * This class is used to return all the necessary information to manage Virtual Images
 * 
 * @author Oliver
 */

public class VirtualImageResult
{
    private ArrayList<Category> categories;

    private ArrayList<Repository> repositories;

    private ArrayList<Icon> icons;

    private ArrayList<VirtualImageType> virtualImageTypes;

    public VirtualImageResult()
    {
        categories = new ArrayList<Category>();
        repositories = new ArrayList<Repository>();
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories)
    {
        this.categories = categories;
    }

    public ArrayList<Repository> getRepositories()
    {
        return repositories;
    }

    public void setRepositories(ArrayList<Repository> repositories)
    {
        this.repositories = repositories;
    }

    public ArrayList<Icon> getIcons()
    {
        return icons;
    }

    public void setIcons(ArrayList<Icon> icons)
    {
        this.icons = icons;
    }

    public ArrayList<VirtualImageType> getVirtualImageTypes()
    {
        return virtualImageTypes;
    }

    public void setVirtualImageTypes(ArrayList<VirtualImageType> virtualImageTypes)
    {
        this.virtualImageTypes = virtualImageTypes;
    }

}
