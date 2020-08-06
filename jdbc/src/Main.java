import java.io.*;
import java.util.*;

public class Main
{
    final static int EXTRA_RUNS_YEAR = 2016;
    final static int BOWLER_ECONOMY_YEAR = 2015;
    final static int TOP_SCORER_BATSMAN_YEAR = 2017;
    final static int BALLS_PER_OVER = 6;
    final static int STRIKE_RATE_CALCULATION_CONSTANT = 100;

    public static void main(String[] args)
    {
        List<Match> matchDetails;
        List<Delivery> deliveryDetails;

        matchDetails = IPLDao.getMatchDetails();
        deliveryDetails = IPLDao.getDeliveryDetails();

        getMatchesPlayedPerYear(matchDetails);
        getMatchesWonByEachTeam(matchDetails);
        getExtraRunsGivenByEachTeam(matchDetails, deliveryDetails);
        getTopEconomicalBowlers(matchDetails, deliveryDetails);
        getTopBatsmanBasedOnStrikeRate(matchDetails, deliveryDetails);
    }

    private static void getMatchesPlayedPerYear(List<Match> matchDetails)
    {
        LinkedHashMap<Integer, Integer> matchFrequency;
        int i, size, count;
        int year;

        matchFrequency = new LinkedHashMap<Integer, Integer>();
        size = matchDetails.size();

        for(i = 0; i < size; i++)
        {
            year = matchDetails.get(i).getSeason();

            if(matchFrequency.containsKey(year))
            {
                count = matchFrequency.get(year);
                count = count + 1;
                matchFrequency.put(year, count);
            }
            else
            {
                count = 1;
                matchFrequency.put(year, count);
            }
        }

        System.out.println("Matches played per year: ");
        System.out.println(matchFrequency);
        System.out.println();
    }

    private static void getMatchesWonByEachTeam(List<Match> matchDetails)
    {
        LinkedHashMap<String, Integer> winnerFrequency;
        int i, size, count;
        String teamName;

        winnerFrequency = new LinkedHashMap<String, Integer>();
        size = matchDetails.size();

        for(i = 0; i < size; i++)
        {
            teamName = matchDetails.get(i).getWinner();

            if(teamName != null)
            {
                if(winnerFrequency.containsKey(teamName))
                {
                    count = winnerFrequency.get(teamName);
                    count = count + 1;
                    winnerFrequency.put(teamName, count);
                }
                else
                {
                    count = 1;
                    winnerFrequency.put(teamName, count);
                }
            }
        }

        System.out.println("Number of matches won by each team: ");
        System.out.println(winnerFrequency);
        System.out.println();
    }

    private static void getExtraRunsGivenByEachTeam(List <Match> matchDetails, List<Delivery> deliveryDetails)
    {
        List<Integer> matchIdList;
        LinkedHashMap<String, Integer> extraRunsData;
        int i, matchId, extraRuns, totalExtraRuns, size;
        String teamName;

        matchIdList = matchIdListGenerator(matchDetails, EXTRA_RUNS_YEAR);
        extraRunsData = new LinkedHashMap<String, Integer>();
        size = deliveryDetails.size();

        for(i = 0; i < size; i++)
        {
            matchId = deliveryDetails.get(i).getMatchId();

            if(matchIdList.contains(matchId))
            {
                teamName = deliveryDetails.get(i).getBowlingTeam();

                if(extraRunsData.containsKey(teamName))
                {
                    extraRuns = deliveryDetails.get(i).getExtraRuns();
                    totalExtraRuns = extraRunsData.get(teamName);
                    totalExtraRuns = totalExtraRuns + extraRuns;
                    extraRunsData.put(teamName, totalExtraRuns);
                }
                else
                {
                    extraRuns = deliveryDetails.get(i).getExtraRuns();
                    extraRunsData.put(teamName, extraRuns);
                }
            }
        }

        System.out.println("Extra Runs Given By Each Team in " + EXTRA_RUNS_YEAR + ": ");
        System.out.println(extraRunsData);
        System.out.println();
    }

    private static void getTopEconomicalBowlers(List<Match> matchDetails, List<Delivery> deliveryDetails)
    {
        List<Integer> matchIdList, bowlerDetail;
        LinkedHashMap<String, List<Integer>> bowlerData;
        int i, size, matchId, runs, totalRuns, totalBalls;
        String bowlerName;

        matchIdList = matchIdListGenerator(matchDetails, BOWLER_ECONOMY_YEAR);
        bowlerData = new LinkedHashMap<String, List<Integer>>();
        size = deliveryDetails.size();

        for(i = 0; i < size; i++)
        {
            matchId = deliveryDetails.get(i).getMatchId();

            if(matchIdList.contains(matchId))
            {
                bowlerName = deliveryDetails.get(i).getBowler();
                runs = deliveryDetails.get(i).getTotalRuns();

                if(bowlerData.containsKey(bowlerName))
                {
                    bowlerDetail = bowlerData.get(bowlerName);
                    totalRuns = bowlerDetail.get(0);
                    totalBalls = bowlerDetail.get(1);

                    totalRuns = totalRuns + runs;
                    totalBalls = totalBalls + 1;

                    bowlerDetail.set(0, totalRuns);
                    bowlerDetail.set(1, totalBalls);
                }
                else
                {
                    bowlerDetail = new ArrayList<Integer>();

                    bowlerDetail.add(runs);
                    bowlerDetail.add(1);
                }
                bowlerData.put(bowlerName, bowlerDetail);
            }
        }

        System.out.println("Bowler Economy Data: ");
        System.out.println(bowlerData);
        System.out.println();

        calculateBowlerEconomy(bowlerData);
    }

    private static void calculateBowlerEconomy(LinkedHashMap<String, List<Integer>> bowlerData)
    {
        LinkedHashMap<String, Float> bowlerEconomyData;
        int i, size, balls, runs;
        float economy;
        String bowlerName[], bowler;
        Integer bowlerDataArr[];
        List<Integer> oneBowlerData;

        bowlerEconomyData = new LinkedHashMap<String, Float>();
        size = bowlerData.size();
        bowlerName = bowlerData.keySet().toArray(new String[bowlerData.keySet().size()]);

        for(i = 0; i < size; i++)
        {
            bowler = bowlerName[i];
            oneBowlerData = bowlerData.get(bowler);
            bowlerDataArr = oneBowlerData.toArray(new Integer[2]);

            runs = bowlerDataArr[0];
            balls = bowlerDataArr[1];

            economy = (float)runs / ((float)balls / BALLS_PER_OVER);

            bowlerEconomyData.put(bowler, economy);
        }

        System.out.println("Bowler Economy Data: ");
        System.out.println(bowlerEconomyData);
        System.out.println();

        sortBowlerEconomyData(bowlerEconomyData);
    }

    private static void sortBowlerEconomyData(LinkedHashMap<String, Float> bowlerEconomyData)
    {
        LinkedHashMap<String, Float> sortedBowlerEconomyData;
        List<Map.Entry<String, Float>> economyList;

        economyList = new LinkedList<Map.Entry<String, Float>>(bowlerEconomyData.entrySet());
        sortedBowlerEconomyData = new LinkedHashMap<String, Float>();

        Collections.sort(economyList, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Float> mapEntry : economyList)
        {
            sortedBowlerEconomyData.put(mapEntry.getKey(), mapEntry.getValue());
        }

        System.out.println("Bowler Economy Data Sorted: ");
        System.out.println(sortedBowlerEconomyData);
        System.out.println();
    }

    private static void getTopBatsmanBasedOnStrikeRate(List<Match> matchDetails, List<Delivery> deliveryDetails)
    {
        List<Integer> matchIdList, batsmanDetail;
        LinkedHashMap<String, List<Integer>> batsmanData;
        int i, size, matchId, runs, totalRuns, totalBalls;
        String batsmanName;

        matchIdList = matchIdListGenerator(matchDetails, TOP_SCORER_BATSMAN_YEAR);
        batsmanData = new LinkedHashMap<String, List<Integer>>();
        size = deliveryDetails.size();

        for(i = 0; i < size; i++)
        {
            matchId = deliveryDetails.get(i).getMatchId();

            if(matchIdList.contains(matchId))
            {
                batsmanName = deliveryDetails.get(i).getBatsman();
                runs = deliveryDetails.get(i).getBatsmanRuns();

                if(batsmanData.containsKey(batsmanName))
                {
                    batsmanDetail = batsmanData.get(batsmanName);
                    totalRuns = batsmanDetail.get(0);
                    totalBalls = batsmanDetail.get(1);

                    totalRuns = totalRuns + runs;
                    totalBalls = totalBalls + 1;

                    batsmanDetail.set(0, totalRuns);
                    batsmanDetail.set(1, totalBalls);
                }
                else
                {
                    batsmanDetail = new ArrayList<Integer>();

                    batsmanDetail.add(runs);
                    batsmanDetail.add(1);
                }
                batsmanData.put(batsmanName, batsmanDetail);
            }
        }

        System.out.println("Batsman Detail: ");
        System.out.println(batsmanData);
        System.out.println();

        calculateBatsmanStrikeRate(batsmanData);
    }

    private static void calculateBatsmanStrikeRate(LinkedHashMap<String, List<Integer>> batsmanData)
    {
        LinkedHashMap<String, Float> batsmanStrikeRateData;
        int i, size, balls, runs;
        float strikeRate;
        String batsmanName[], batsman;
        Integer batsmanDataArr[];
        List<Integer> oneBatsmanData;

        batsmanStrikeRateData = new LinkedHashMap<String, Float>();
        size = batsmanData.size();
        batsmanName = batsmanData.keySet().toArray(new String[batsmanData.keySet().size()]);

        for(i = 0; i < size; i++)
        {
            batsman = batsmanName[i];
            oneBatsmanData = batsmanData.get(batsman);
            batsmanDataArr = oneBatsmanData.toArray(new Integer[2]);

            runs = batsmanDataArr[0];
            balls = batsmanDataArr[1];

            strikeRate = ((float)runs / balls) * STRIKE_RATE_CALCULATION_CONSTANT;

            batsmanStrikeRateData.put(batsman, strikeRate);
        }

        System.out.println("Batsman Strike Rate Data: ");
        System.out.println(batsmanStrikeRateData);
        System.out.println();

        sortBatsmanStrikeRateData(batsmanStrikeRateData);
    }

    private static void sortBatsmanStrikeRateData(LinkedHashMap<String, Float> batsmanStrikeRateData)
    {
        LinkedHashMap<String, Float> sortedBatsmanStrikeRateData;
        List<Map.Entry<String, Float>> strikeRateList;

        strikeRateList = new LinkedList<Map.Entry<String, Float>>(batsmanStrikeRateData.entrySet());
        sortedBatsmanStrikeRateData = new LinkedHashMap<String, Float>();

        Collections.sort(strikeRateList, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Float> mapEntry : strikeRateList)
        {
            sortedBatsmanStrikeRateData.put(mapEntry.getKey(), mapEntry.getValue());
        }

        System.out.println("Batsman Strike Rate Data Sorted: ");
        System.out.println(sortedBatsmanStrikeRateData);
        System.out.println();
    }

    private static List<Integer> matchIdListGenerator(List<Match> matchDetail, int dataYear)
    {
        List<Integer> matchIdList;
        int i, size, year, matchId;

        matchIdList = new ArrayList<Integer>();
        size = matchDetail.size();

        for(i = 0; i < size; i++)
        {
            year = matchDetail.get(i).getSeason();

            if(year == dataYear)
            {
                matchId = matchDetail.get(i).getId();
                matchIdList.add(matchId);
            }
        }

        return matchIdList;
    }
}