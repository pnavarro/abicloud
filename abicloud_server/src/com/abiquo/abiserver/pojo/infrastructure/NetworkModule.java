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
package com.abiquo.abiserver.pojo.infrastructure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.DnsHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.NetworkmoduleHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.NetworkmoduleIdHB;
import com.abiquo.abiserver.pojo.IPojo;

public class NetworkModule implements IPojo
{

    /* ------------- Public atributes ------------- */
    private int id;

    private Boolean dhcp;

    private String ip;

    private String subNetMask;

    private String gateway;

    private BigDecimal bw;

    private ArrayList<DNS> dns;

    /* ------------- Constructor ------------- */
    public NetworkModule()
    {
        id = 0;
        dhcp = false;
        ip = "";
        subNetMask = "";
        gateway = "";
        bw = new BigDecimal(0);
        dns = new ArrayList<DNS>();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Boolean getDhcp()
    {
        return dhcp;
    }

    public void setDhcp(Boolean dhcp)
    {
        this.dhcp = dhcp;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getSubNetMask()
    {
        return subNetMask;
    }

    public void setSubNetMask(String subNetMask)
    {
        this.subNetMask = subNetMask;
    }

    public String getGateway()
    {
        return gateway;
    }

    public void setGateway(String gateway)
    {
        this.gateway = gateway;
    }

    public BigDecimal getBw()
    {
        return bw;
    }

    public void setBw(BigDecimal bw)
    {
        this.bw = bw;
    }

    public ArrayList<DNS> getDns()
    {
        return dns;
    }

    public void setDns(ArrayList<DNS> dns)
    {
        this.dns = dns;
    }

    public IPojoHB toPojoHB()
    {
        NetworkmoduleHB networkModuleHB = new NetworkmoduleHB();

        NetworkmoduleIdHB networkModuleId = new NetworkmoduleIdHB();
        networkModuleId.setIdNetworkModule(this.id);
        networkModuleHB.setId(networkModuleId);

        networkModuleHB.setDhcp(this.dhcp ? 1 : 0);
        networkModuleHB.setIp(this.getIp());
        networkModuleHB.setGateway(this.getGateway());
        networkModuleHB.setsubnetMask(this.subNetMask);
        networkModuleHB.setBw(this.bw);

        // Setting the DNS
        Set<DnsHB> dnsHBList = new HashSet<DnsHB>();
        DnsHB dnsHB;
        for (DNS dns : this.dns)
        {
            dnsHB = (DnsHB) dns.toPojoHB();
            dnsHBList.add(dnsHB);
        }

        networkModuleHB.setDnses(dnsHBList);

        return networkModuleHB;
    }

}
