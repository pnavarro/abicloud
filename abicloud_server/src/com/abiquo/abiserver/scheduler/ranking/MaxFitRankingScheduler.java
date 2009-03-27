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
package com.abiquo.abiserver.scheduler.ranking;

import org.apache.log4j.Logger;

import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;

/**
 * Better machines (highest rank) for fully PhysicalMachine.
 */
public class MaxFitRankingScheduler extends RankingSchedulerRestriction
{

    private final static boolean isPrintCompute = true;

    private final static Logger log = Logger.getLogger(MaxFitRankingScheduler.class);

    @Override
    int computeRanking(PhysicalMachine machine)
    {
        int rank;
        double pHd, pRam;

        pRam = (machine.getRamUsed() / machine.getRam()) * 100;
        pHd = (machine.getHdUsed() / machine.getHd()) * 100;

        rank = (int) ((pRam + pHd) * 100 / 2);

        if (isPrintCompute)
        {
            log.debug("rank for " + machine.getName() + " \t rank:" + rank + "\t \trma:" + pRam
                + "\thd:" + pHd + ")");
        }

        return rank;
    }

}
