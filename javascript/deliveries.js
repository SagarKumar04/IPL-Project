class Deliveries
{
  constructor()
  {

  }

  set matchId(match_id)
  {
    this._match_id = match_id;
  }

  set inning(inning)
  {
    this._inning = inning;
  }

  set battingTeam(batting_team)
  {
    this._batting_team = batting_team;
  }

  set bowlingTeam(bowling_team)
  {
    this._bowling_team = bowling_team;
  }

  set over(over)
  {
    this._over = over;
  }

  set ball(ball)
  {
    this._ball = ball;
  }

  set batsman(batsman)
  {
    this._batsman = batsman;
  }

  set nonStriker(non_striker)
  {
    this._non_striker = non_striker;
  }

  set bowler(bowler)
  {
    this._bowler = bowler;
  }

  set isSuperOver(is_super_over)
  {
    this._is_super_over = is_super_over;
  }

  set wideRuns(wide_runs)
  {
    this._wide_runs = wide_runs;
  }

  set byeRuns(bye_runs)
  {
    this._bye_runs = bye_runs;
  }

  set legByeRuns(legbye_runs)
  {
    this._legbye_runs = legbye_runs;
  }

  set noBallRuns(noball_runs)
  {
    this._noball_runs = noball_runs;
  }

  set penaltyRuns(penalty_runs)
  {
    this._penalty_runs = penalty_runs;
  }

  set batsmanRuns(batsman_runs)
  {
    this._batsman_runs = batsman_runs;
  }

  set extraRuns(extra_runs)
  {
    this._extra_runs = extra_runs;
  }

  set totalRuns(total_runs)
  {
    this._total_runs = total_runs;
  }

  set playerDismissed(player_dismissed)
  {
    this._player_dismissed = player_dismissed;
  }

  set dismissalKind(dismissal_kind)
  {
    this._dismissal_kind = dismissal_kind;
  }

  set fielder(fielder)
  {
    this._fielder = fielder;
  }

  get matchId()
  {
    return this._match_id;
  }

  get inning()
  {
    return this._inning;
  }

  get battingTeam()
  {
    return this._batting_team;
  }

  get bowlingTeam()
  {
    return this._bowling_team;
  }

  get over()
  {
    return this._over;
  }

  get ball()
  {
    return this._ball;
  }

  get batsman()
  {
    return this._batsman;
  }

  get nonStriker()
  {
    return this._non_striker;
  }

  get bowler()
  {
    return this._bowler;
  }

  get isSuperOver()
  {
    return this._is_super_over;
  }

  get wideRuns()
  {
    return this._wide_runs;
  }

  get byeRuns()
  {
    return this._bye_runs;
  }

  get legByeRuns()
  {
    return this._legbye_runs;
  }

  get noBallRuns()
  {
    return this._noball_runs;
  }

  get penaltyRuns()
  {
    return this._penalty_runs;
  }

  get batsmanRuns()
  {
    return this._batsman_runs;
  }

  get extraRuns()
  {
    return this._extra_runs;
  }

  get totalRuns()
  {
    return this._total_runs;
  }

  get playerDismissed()
  {
    return this._player_dismissed;
  }

  get dismissalKind()
  {
    return this._dismissal_kind;
  }

  get fielder()
  {
    return this._fielder;
  }

}

function main()
{
  getDeliveryData();
}

function getDeliveryData()
{
  const csv = require('csvtojson');

  const converter = csv().fromFile('csv/deliveries.csv').then((deliveries) => {
    let delivery;
    let deliveriesArr = [];

    for(let d of deliveries)
    {
      delivery = new Deliveries();
      Object.assign(delivery, d);
      deliveriesArr.push(delivery);
    }

    getExtraRunsConcededPerTeam(deliveriesArr);
    getTopEconomicalBowlers(deliveriesArr);
    getTopBatsmanAccordingToStrikeRate(deliveriesArr);
  });
}

function getExtraRunsConcededPerTeam(deliveriesArr)
{
  let i, length, teamName, matchId;
  let extraRunsData = {};

  length = deliveriesArr.length;

  for(i = 0; i < length; i++)
  {
    matchId = deliveriesArr[i].match_id;
    teamName = deliveriesArr[i].bowling_team;

    if(matchId >= extraRunsStartMatchId && matchId <= extraRunsEndMatchId)
    {
      if(extraRunsData[teamName] === undefined)
      {
        extraRunsData[teamName] = parseInt(deliveriesArr[i].extra_runs);
      }
      else
      {
        extraRunsData[teamName] = extraRunsData[teamName] + parseInt(deliveriesArr[i].extra_runs);
      }
    }
  }
  console.log(extraRunsData);
}

function getTopEconomicalBowlers(deliveriesArr)
{
  let i, length, bowlerName, matchId, totalRuns, totalBalls, bowlerEconomy;
  let bowlerTotalRunsGiven = {}, bowlerTotalBalls = {}, bowlerEconomyData = {};

  length = deliveriesArr.length;

  for(i = 0; i < length; i++)
  {
    matchId = deliveriesArr[i].match_id;
    bowlerName = deliveriesArr[i].bowler;

    if(matchId >= economicalBowlersStartMatchId && matchId <= economicalBowlersEndMatchId)
    {
      if(bowlerTotalRunsGiven[bowlerName] == undefined)
      {
        bowlerTotalRunsGiven[bowlerName] = parseInt(deliveriesArr[i].total_runs);
        bowlerTotalBalls[bowlerName] = 1;
      }
      else
      {
        bowlerTotalRunsGiven[bowlerName] = bowlerTotalRunsGiven[bowlerName] + parseInt(deliveriesArr[i].total_runs);
        bowlerTotalBalls[bowlerName] = bowlerTotalBalls[bowlerName] + 1;
      }
      totalRuns = bowlerTotalRunsGiven[bowlerName];
      totalBalls = bowlerTotalBalls[bowlerName];
      bowlerEconomy = totalRuns / (totalBalls / 6.0);

      bowlerEconomyData[bowlerName] = bowlerEconomy;
    }
  }
  console.log(bowlerEconomyData);
}

function getTopBatsmanAccordingToStrikeRate(deliveriesArr)
{
  let i, length, batsmanName, matchId, totalRuns, totalBalls, batsmanStrikeRate;
  let batsmanTotalRunsScored = {}, batsmanTotalBallsPlayed = {}, batsmanStrikeRateData = {};

  length = deliveriesArr.length;

  for(i = 0; i < length; i++)
  {
    matchId = deliveriesArr[i].match_id;
    batsmanName = deliveriesArr[i].batsman;

    if(matchId >= batsmanStrikeRateStartMatchId && matchId <= batsmanStrikeRateEndMatchId)
    {
      if(batsmanTotalRunsScored[batsmanName] == undefined)
      {
        batsmanTotalRunsScored[batsmanName] = parseInt(deliveriesArr[i].batsman_runs);
        batsmanTotalBallsPlayed[batsmanName] = 1;
      }
      else
      {
        batsmanTotalRunsScored[batsmanName] = batsmanTotalRunsScored[batsmanName] + parseInt(deliveriesArr[i].batsman_runs);
        batsmanTotalBallsPlayed[batsmanName] = batsmanTotalBallsPlayed[batsmanName] + 1;
      }
      totalRuns = batsmanTotalRunsScored[batsmanName];
      totalBalls = batsmanTotalBallsPlayed[batsmanName];
      batsmanStrikeRate = (totalRuns * 100.0) / totalBalls;

      batsmanStrikeRateData[batsmanName] = batsmanStrikeRate;
    }
  }
  console.log(batsmanStrikeRateData);
}

const extraRunsStartMatchId = 577;
const extraRunsEndMatchId = 636;

const economicalBowlersStartMatchId = 518;
const economicalBowlersEndMatchId = 576;

const batsmanStrikeRateStartMatchId = 518;
const batsmanStrikeRateEndMatchId = 576;

main();
