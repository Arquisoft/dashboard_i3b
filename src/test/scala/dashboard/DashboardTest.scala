package es.uniovi.asw

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class DashboardTest extends Simulation {

  val httpConf = http.baseURL("http://localhost:8090")

  val scn = scenario("DashboardLoadTestLogs").
    exec(http("Root").get("/")).
    pause(3).
    exec(http("logscouncilstaff").get("/councilstaff")).
    pause(3).
    exec(http("LogsOtherAuthorities").get("/otherAuthorities")).
    pause(3).
    exec(http("LogsCouncilmen").get("/councilmen")).
    pause(3)

  setUp(scn.inject(rampUsers(20) over (60 seconds))).
    protocols(httpConf)

}
