package repositories

import java.net.InetAddress
import java.util

import models.Intern
import org.elasticsearch.action.count.CountResponse
import org.elasticsearch.action.delete.DeleteResponse
import org.elasticsearch.action.get.{MultiGetResponse, GetResponse}
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.{SearchRequestBuilder, SearchResponse}
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.XContentFactory._
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryBuilders._
import org.elasticsearch.search.SearchHit
import play.api.libs.json.{JsPath, Writes, Json, JsValue}
import play.api.libs.functional.syntax._


/**
  * Created by sahil on 4/10/16.
  */
trait InternTable {

  val port = 9300
  val nodes = List("localhost")
  val addresses = nodes.map { host => new InetSocketTransportAddress(InetAddress.getByName(host), port) }
  lazy val settings = Settings.settingsBuilder()
    .put("threadpool.search.queue_size", -1)
    .put("threadpool.index.queue_size", -1)
    .put("client.transport.ping_timeout", "120s")
    .put("client.transport.nodes_sampler_interval", "60s")
    .put("cluster.name", "sahil55")
    .build()
  val client: Client = TransportClient.builder().settings(settings).build().addTransportAddresses(addresses: _*)
}

class InternRepo extends InternTable {

  def getAll={

    Thread.sleep(500)
    val arrayy = client.prepareSearch("knoldus").setQuery(matchAllQuery).execute.actionGet.getHits.getHits
    var str=client.prepareSearch("knoldus").setQuery(matchAllQuery).execute.actionGet.getHits.getHits.apply(0).getSourceAsString
    for(i <- 1 until arrayy.length){
      str=str+","+client.prepareSearch("knoldus").setQuery(matchAllQuery).execute.actionGet.getHits.getHits.apply(i).getSourceAsString
    }
    "["+str+"]"
  }


  def delete(id: String): String = client.prepareDelete("knoldus", "interns", id.toString).get.getId

  def internById(id: String): String = client.prepareGet("knoldus", "interns", id.toString).get.getSourceAsString

  def insert(intern: String): String = client.prepareIndex("knoldus", "interns").setSource(intern.toString).get.getId

  def update(intern: String, id: String): String = client.prepareUpdate("knoldus", "interns", id).setDoc(intern).get.getId
}








