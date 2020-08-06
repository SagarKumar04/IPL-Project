class Matches
{
  constructor()
  {

  }

  set id(id)
  {
    this._id = id;
  }

  set season(season)
  {
    this._season = season;
  }

  set city(city)
  {
    this._city = city;
  }

  set date(date)
  {
    this._date = date;
  }

  set team1(team1)
  {
    this._team1 = team1;
  }

  set team2(team2)
  {
    this._team2 = team2;
  }

  set tossWinner(toss_winner)
  {
    this._toss_winner = toss_winner;
  }

  set tossDecision(toss_decision)
  {
    this._toss_decision = toss_decision;
  }

  set result(result)
  {
    this._result = result;
  }

  set dlApplied(dl_applied)
  {
    this._dl_applied = dl_applied;
  }

  set winner(winner)
  {
    this._winner = winner;
  }

  set win_by_runs(win_by_runs)
  {
    this._win_by_runs = win_by_runs;
  }

  set win_by_wickets(win_by_wickets)
  {
    this._win_by_wickets = win_by_wickets;
  }

  set playerOfMatch(player_of_match)
  {
    this._player_of_match = player_of_match;
  }

  set venue(venue)
  {
    this._venue = venue;
  }

  set umpire1(umpire1)
  {
    this._umpire1 = umpire1;
  }

  set umpire2(umpire2)
  {
    this._umpire2 = umpire2;
  }

  set umpire3(umpire3)
  {
    this._umpire3 = umpire3;
  }

  get id()
  {
    return this._id;
  }

  get season()
  {
    return this._season;
  }

  get city()
  {
    return this._city;
  }

  get date()
  {
    return this._date;
  }

  get team1()
  {
    return this._team1;
  }

  get team2()
  {
    return this._team2;
  }

  get tossWinner()
  {
    return this._toss_winner;
  }

  get tossDecision()
  {
    return this._toss_decision;
  }

  get result()
  {
    return this._result;
  }

  get dlApplied()
  {
    return this._dl_applied;
  }

  get winner()
  {
    return this._winner;
  }

  get winByRuns()
  {
    return this._win_by_runs;
  }

  get winByWickets()
  {
    return this._win_by_wickets;
  }

  get playerOfMatch()
  {
    return this._player_of_match;
  }

  get venue()
  {
    return this._venue;
  }

  get umpire1()
  {
    return this._umpire1;
  }

  get umpire2()
  {
    return this._umpire2;
  }

  get umpire3()
  {
    return this._umpire3;
  }
}

function main()
{
  getMatchData();
}

function getMatchData()
{
  const csv = require('csvtojson');

  const converter = csv().fromFile('csv/matches.csv').then((matches) => {
    let match;
    let matchesArr = [];

    for(let m of matches)
    {
      match = new Matches();
      Object.assign(match, m);
      matchesArr.push(match);
    }

    findNumberOfMatchesPlayedPerYear(matchesArr);
    findNumberOfMatchesWonByEachTeam(matchesArr);
  });
}

function findNumberOfMatchesPlayedPerYear(matchesArr)
{
  let i, length, year;
  let matchFrequency = {};

  length = matchesArr.length;

  for(i = 0; i < length; i++)
  {
    year = matchesArr[i].season;
    if(matchFrequency[year] === undefined)
    {
      matchFrequency[year] = 1;
    }
    else
    {
      matchFrequency[year] = matchFrequency[year] + 1;
    }
  }
  console.log(matchFrequency);
}

function findNumberOfMatchesWonByEachTeam(matchesArr)
{
  let i, length, teamName;
  let winFrequency = {};

  length = matchesArr.length;

  for(i = 0; i < length; i++)
  {
    teamName = matchesArr[i].winner;
    if(winFrequency[teamName] === undefined)
    {
      winFrequency[teamName] = 1;
    }
    else
    {
      winFrequency[teamName] = winFrequency[teamName] + 1;
    }
  }
  console.log(winFrequency);
}

main();
