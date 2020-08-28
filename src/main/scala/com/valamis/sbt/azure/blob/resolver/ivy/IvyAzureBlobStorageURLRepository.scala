package com.valamis.sbt.azure.blob.resolver.ivy

import java.net.URL

import com.valamis.sbt.azure.blob.resolver.utils.AzureAsyncClient
import com.valamis.sbt.azure.blob.resolver.{AzureBlobStorageConfig, AzureBlobStorageCredentialsProvider, AzureBlobStorageRef}
import com.valamis.sbt.azure.blob.resolver.utils.AsyncUtils.{AtMost, _}
import org.apache.ivy.plugins.repository.url.URLRepository

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits._

/**
  * created by lukaci on 15/03/2019.
  */

class IvyAzureBlobStorageURLRepository(config: AzureBlobStorageConfig = AzureBlobStorageConfig.default, provider: AzureBlobStorageCredentialsProvider = AzureBlobStorageCredentialsProvider.default) extends URLRepository {
  private lazy val client = new AzureAsyncClient()

  override def list(parent: String): java.util.List[_] = {
    val url = new URL(parent)
    implicit val tmo: AtMost = AtMost(config.shortOpDuration)
    implicit val ref: AzureBlobStorageRef = AzureBlobStorageRef.fromUrl(url, config, provider)
    this.client.list.map(_.map(x => new URL(s"${config.scheme}://${ref.accountName}/${x.getName.stripPrefix("/")}").toExternalForm)).await.asJava
  }
}


