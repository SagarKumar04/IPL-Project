import java.sql.*;
import java .util.*;

public class IPLDao
{
    public static Connection getConnection()
    {
        Connection connectionObj = null;

        String url, userName, password;

        url = "jdbc:postgresql://localhost:5432/ipldata";
        userName = "postgres";
        password = "password";

        try
        {
            Class.forName("org.postgresql.Driver");
            connectionObj = DriverManager.getConnection(url, userName, password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return connectionObj;
    }

    public static List<Match> getMatchDetails()
    {
        List<Match> matchDetails;

        matchDetails = new ArrayList<Match>();

        try
        {
            Connection connectionObj;
            PreparedStatement queryStatement;
            ResultSet queryResult;

            connectionObj = getConnection();
            queryStatement = connectionObj.prepareStatement("select * from matches");
            queryResult = queryStatement.executeQuery();

            while(queryResult.next())
            {
                Match matchObj = new Match();

                matchObj.setId(queryResult.getInt("id"));
                matchObj.setSeason(queryResult.getInt("season"));
                matchObj.setCity(queryResult.getString("city"));
                matchObj.setDate(queryResult.getString("date"));
                matchObj.setTeam1(queryResult.getString("team1"));
                matchObj.setTeam2(queryResult.getString("team2"));
                matchObj.setTossWinner(queryResult.getString("toss_winner"));
                matchObj.setTossDecision(queryResult.getString("toss_decision"));
                matchObj.setResult(queryResult.getString("result"));
                matchObj.setDlApplied(queryResult.getInt("dl_applied"));
                matchObj.setWinner(queryResult.getString("winner"));
                matchObj.setWinByRuns(queryResult.getInt("win_by_runs"));
                matchObj.setWinByWickets(queryResult.getInt("win_by_wickets"));
                matchObj.setPlayerOfMatch(queryResult.getString("player_of_match"));
                matchObj.setVenue(queryResult.getString("venue"));
                //matchObj.setUmpire1(queryResult.getString("umpire1"));
                //matchObj.setUmpire2(queryResult.getString("umpire2"));
                //matchObj.setUmpire3(queryResult.getString("umpire3"));

                matchDetails.add(matchObj);
            }
            connectionObj.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return matchDetails;
    }

    public static List<Delivery> getDeliveryDetails()
    {
        List<Delivery> deliveryDetails;

        deliveryDetails = new ArrayList<Delivery>();

        try
        {
            Connection connectionObj;
            PreparedStatement queryStatement;
            ResultSet queryResult;

            connectionObj = getConnection();
            queryStatement = connectionObj.prepareStatement("select * from deliveries");
            queryResult = queryStatement.executeQuery();

            while(queryResult.next())
            {
                Delivery deliveryObj = new Delivery();

                deliveryObj.setMatchId(queryResult.getInt("match_id"));
                deliveryObj.setInning(queryResult.getInt("inning"));
                deliveryObj.setBattingTeam(queryResult.getString("batting_team"));
                deliveryObj.setBowlingTeam(queryResult.getString("bowling_team"));
                deliveryObj.setOver(queryResult.getInt("over"));
                deliveryObj.setBall(queryResult.getInt("ball"));
                deliveryObj.setBatsman(queryResult.getString("batsman"));
                deliveryObj.setNonStriker(queryResult.getString("non_striker"));
                deliveryObj.setBowler(queryResult.getString("bowler"));
                deliveryObj.setIsSuperOver(queryResult.getInt("is_super_over"));
                deliveryObj.setWideRuns(queryResult.getInt("wide_runs"));
                deliveryObj.setByeRuns(queryResult.getInt("bye_runs"));
                deliveryObj.setLegByeRuns(queryResult.getInt("legbye_runs"));
                deliveryObj.setNoBallRuns(queryResult.getInt("noball_runs"));
                deliveryObj.setPenaltyRuns(queryResult.getInt("penalty_runs"));
                deliveryObj.setBatsmanRuns(queryResult.getInt("batsman_runs"));
                deliveryObj.setExtraRuns(queryResult.getInt("extra_runs"));
                deliveryObj.setTotalRuns(queryResult.getInt("total_runs"));
                //deliveryObj.setPlayerDismissed(queryResult.getString("player_dismissed"));
                //deliveryObj.setDismissalKind(queryResult.getString("dismissal_kind"));
                //deliveryObj.setFielder(queryResult.getString("fielder"));


                deliveryDetails.add(deliveryObj);
            }
            connectionObj.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return deliveryDetails;
    }

    public static void main(String args[])
    {

    }
}
