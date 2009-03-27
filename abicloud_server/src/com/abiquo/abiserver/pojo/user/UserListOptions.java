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
package com.abiquo.abiserver.pojo.user;

public class UserListOptions
{
    private int offset;

    private int length;

    private String filter;

    private String orderBy;

    private Boolean asc;

    private Enterprise byEnterprise;

    private Boolean loggedOnly;

    public UserListOptions()
    {
        offset = 0;
        length = 100;
        filter = "";
        orderBy = "name";
        asc = true;
        byEnterprise = null;
        loggedOnly = false;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public String getFilter()
    {
        return filter;
    }

    public void setFilter(String filter)
    {
        this.filter = filter;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public Boolean getAsc()
    {
        return asc;
    }

    public void setAsc(Boolean asc)
    {
        this.asc = asc;
    }

    public Enterprise getByEnterprise()
    {
        return byEnterprise;
    }

    public void setByEnterprise(Enterprise byEnterprise)
    {
        this.byEnterprise = byEnterprise;
    }

    public Boolean getLoggedOnly()
    {
        return loggedOnly;
    }

    public void setLoggedOnly(Boolean loggedOnly)
    {
        this.loggedOnly = loggedOnly;
    }
}
