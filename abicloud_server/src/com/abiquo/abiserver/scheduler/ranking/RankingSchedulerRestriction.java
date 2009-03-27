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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;

import org.apache.log4j.Logger;

import com.abiquo.abiserver.scheduler.SchedulerRestrictions;

/**
 * Implements the abstract select using a PhysicalMachine heuristic ranking, for each
 * PhysicalMachine on the candidate list is generated a ranking, then the highest ranking
 * PhysicalMachine is selected.
 */
public abstract class RankingSchedulerRestriction extends SchedulerRestrictions
{
    private final static Logger log = Logger.getLogger(RankingSchedulerRestriction.class);

    private final static boolean isPrintRankEnable = true;

    /**
     * Generate an integer value representing PhysicalMachine goodness. Strategy pattern
     * implementation.
     * 
     * @param machine the PhysicalMachine to compute its rank
     * @return an heuristic of the machine goodness (greater rank, better machine).
     */
    abstract int computeRanking(PhysicalMachine machine);

    @Override
    public PhysicalMachine select(List<PhysicalMachine> candidateMachines)
    {
        return sortPhysicalMachines(candidateMachines).get(0).getMachine();
    }

    /**
     * Compute rank for all candidate PhysicalMachines, the sort and return in decreasing order.
     */
    private List<PhysicalMachineRanking> sortPhysicalMachines(
        List<PhysicalMachine> candidateMachines)
    {
        List<PhysicalMachineRanking> ranking;
        PhysicalMachineRanking rank;

        ranking = new ArrayList<PhysicalMachineRanking>();

        for (PhysicalMachine pm : candidateMachines)
        {
            rank = new PhysicalMachineRanking(pm);
            rank.setRanking(computeRanking(pm));
            ranking.add(rank);
        }

        Collections.sort(ranking);

        if (isPrintRankEnable)
        {
            printRanking(ranking);
        }

        return ranking;
    }

    private void printRanking(List<PhysicalMachineRanking> ranking)
    {
        for (PhysicalMachineRanking nr : ranking)
        {
            log.debug(nr.getRanking() + "\t machine " + nr.getMachine().getName());
        }
    }

}

/**
 * Maintains a PhysicalMachine and its rank (comparable throw rank property)
 */
class PhysicalMachineRanking implements Comparable<PhysicalMachineRanking>
{
    PhysicalMachine machine;

    int ranking;

    public PhysicalMachineRanking(PhysicalMachine machine)
    {
        super();

        this.machine = machine;
        this.ranking = 0;
    }

    public PhysicalMachine getMachine()
    {
        return machine;
    }

    public int getRanking()
    {
        return ranking;
    }

    public void setRanking(int ranking)
    {
        this.ranking = ranking;
    }

    public int compareTo(PhysicalMachineRanking o)
    {
        return (o.getRanking() - ranking);
    }
}
