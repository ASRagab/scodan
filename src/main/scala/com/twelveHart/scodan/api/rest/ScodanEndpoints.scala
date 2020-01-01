package com.twelveHart.scodan.api.rest

import sttp.client._
import sttp.model.Uri
import com.twelveHart.scodan.api._

trait ScodanEndpoints {
  protected val baseUri: String

  // Shodan Search Methods
  protected def hostInfoEndpoint(ip: String, params: Map[String, String])(apiKey: String): Uri =
    uri"$baseUri/shodan/host/$ip?key=$apiKey&$params"

  protected def hostCountEndpoint(query: String, facets: Option[String])(apiKey: String): Uri =
    uri"$baseUri/shodan/host/count?key=$apiKey&${makeParams(query, facets)}"

  protected def hostSearchEndpoint(query: String, facets: Option[String])(apiKey: String): Uri =
    uri"$baseUri/shodan/host/search?key=$apiKey&${makeParams(query, facets)}"

  protected def hostSearchTokensEndpoint(query: String)(apiKey: String): Uri =
    uri"$baseUri/shodan/host/search?key=$apiKey&${makeParams(query)}"

  protected def portsEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/ports?key=$apiKey"

  // Shodan On-Demand Scanning
  protected def protocolsEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/protocols?key=$apiKey"

  protected def scanEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/scan?key=$apiKey"

  protected def scanInternetEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/scan/internet?key=$apiKey"

  protected def scanByIdEndpoint(scanId: String)(apiKey: String): Uri =
    uri"$baseUri/shodan/scan/$scanId?key=$apiKey"

  // Shodan Network Alerts
  protected def createAlertEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/alert?key=$apiKey"

  protected def alertInfoByIdEndpoint(alertId: String)(apiKey: String): Uri =
    uri"$baseUri/shodan/alert/$alertId/info?key=$apiKey"

  protected def deleteAlertEndpoint(alertId: String)(apiKey: String): Uri =
    uri"$baseUri/shodan/alert/$alertId?key=$apiKey"

  protected def alertInfoEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/alert/info?key=$apiKey"

  protected def notifierAlertEndpoint(alertId: String, notifierId: String)(apiKey: String): Uri =
    uri"$baseUri/shodan/alert/$alertId/notifier/$notifierId?key=$apiKey"

  protected def alertTriggersEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/alert/triggers?key=$apiKey"

  protected def alertByIdTriggerEndpoint(alertId: String, triggerName: String)(apiKey: String): Uri =
    uri"$baseUri/shodan/alert/$alertId/trigger/$triggerName?key=$apiKey"

  protected def alertTriggerIgnoreServiceEndpoint(alertId: String, triggerName: String, service: String)(
    apiKey: String
  ): Uri =
    uri"$baseUri/shodan/alert/$alertId/trigger/$triggerName/ignore/$service?key=$apiKey"

  // Notifier
  protected def notifierEndpoint(apiKey: String): Uri =
    uri"$baseUri/notifier?key=$apiKey"

  protected def notifierProviderEndpoint(apiKey: String): Uri =
    uri"$baseUri/notifier/provider?key=$apiKey"

  protected def notifierByIdEndpoint(notifierId: String)(apiKey: String): Uri =
    uri"$baseUri/notifier/$notifierId?key=$apiKey"

  // Shodan Directory Methods
  protected def directoryQueryEndppoint(apiKey: String): Uri =
    uri"$baseUri/shodan/query?key=$apiKey"

  protected def directoryQuerySearchEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/query/search?key=$apiKey"

  protected def directoryQueryTagsEndpoint(apiKey: String): Uri =
    uri"$baseUri/shodan/query/tags?key=$apiKey"

  // Shodan Bulk Data Methods (Enterprise Endpoints)
  protected def dataEndpoint(apiKey: String) =
    uri"$baseUri/shodan/data?key=$apiKey"

  protected def datasetEndpoint(dataset: String)(apiKey: String) =
    uri"$baseUri/shodan/data/$dataset?key=$apiKey"

  // Manage Organization (Enterprise Endpoints)
  protected def orgEndpoint(apiKey: String): Uri =
    uri"$baseUri/org?key=$apiKey"

  protected def orgMemberUserEndpoint(user: String)(apiKey: String) =
    uri"$baseUri/org/member/$user?key=$apiKey"
  // Account Methods
  protected def accountProfileEndpoint(apiKey: String): Uri =
    uri"$baseUri/account/profile?key=$apiKey"

  // DNS Methods
  protected def dnsDomainEndpoint(domain: String)(apiKey: String): Uri =
    uri"$baseUri/dns/domain/$domain?key=$apiKey"

  protected def dnsResolveEndpoint(hostnames: List[String])(apiKey: String): Uri = {
    val list = hostnames.mkString(",")
    uri"$baseUri/dns/resolve?hostnames=$list&key=$apiKey"
  }

  protected def dnsReverseEndpoint(ips: List[String])(apiKey: String): Uri = {
    val list = ips.mkString(",")
    uri"$baseUri/dns/reverse?ips=$list&key=$apiKey"
  }

  // Utility Methods
  protected def httpHeadersEndpoint(apiKey: String): Uri =
    uri"$baseUri/tools/httpheaders?key=$apiKey"

  protected def myIpEndpoint(apiKey: String): Uri =
    uri"$baseUri/tools/myip?key=$apiKey"

  //Api Status Methods
  protected def apiInfoEndpoint(apiKey: String): Uri =
    uri"$baseUri/api-info?key=$apiKey"

  // Experimental Methods
  protected def honeyscoreEndpoint(ip: String)(apiKey: String): Uri =
    uri"$baseUri/labs/honeyscore/$ip?key=$apiKey"
}
