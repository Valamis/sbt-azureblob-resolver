# Sbt Azure BlobStorage resolver

Plugin to ease resolving dependencies from and publish to Azure BlobStorage containers, using custom url syntax 'blob://'.

This is a fork of https://github.com/lukaci/sbt-azureblob-resolver with added support for all types of 
credentials - original plugin supports only shared key credentials.

Thanks to [`ohnosequences`](https://github.com/ohnosequences/sbt-s3-resolver) [`gkatzioura`](https://github.com/gkatzioura/CloudStorageMaven) [`frugalmechanic`](https://github.com/frugalmechanic/fm-sbt-s3-resolver) for the job done on other storage providers

## SBT 1.1+ Support

SBT 1.1 support is available using version `>= 0.10.0`:

```scala
addSbtPlugin("com.valamis" %% "sbt-azureblob-resolver" % "0.10.0")
```

## Examples

### Resolving

Maven Style:

```scala
resolvers += "Blob Snapshots" at "blob://youraccountname/snapshots"
```

Ivy Style:

```scala
resolvers += Resolver.url("Blob Snapshots", url("blob://youraccountname/snapshots"))(Resolver.ivyStylePatterns)
```

### Publishing

Maven Style:

```scala
publishMavenStyle := true
publishTo := Some("Blob Snapshots" at "blob://youraccountname/snapshots")
```

Ivy Style:

```scala
publishMavenStyle := false
publishTo := Some(Resolver.url("Blob Snapshots", url("blob://youraccountname/snapshots"))(Resolver.ivyStylePatterns))
```

### Valid blob:// URL Formats

    blob://[ACCOUNTNAME]/[ROOT_CONTAINER]

## Usage

### Add this to your project/plugins.sbt file:

```scala
addSbtPlugin("com.valamis" %% "sbt-azureblob-resolver" % "0.10.0")
```

Note: if you need to resolve other plugins, published to your Azure Blob storage, then you need to put `addSbtPlugin` 
line to `project/project/project/plugins.sbt` file. 
See [here](https://www.scala-sbt.org/1.x/docs/Organizing-Build.html#sbt+is+recursive) for details

### Azure BlobStorage Credentials

Credentials are checked in 
 1. Environment variables (token credentials first)
 2. Specific account name property files (token credentials first)
 
If no credentials found, then **anonymous** access is used 


#### Environment Variables

    SBT_BLOB_TOKEN_CREDENTIALS=<ACCOUNT_NAME_1>:<SECRET_KEY_1>:<ACCOUNT_NAME_2>:<SECRET_KEY_2>:...
    SBT_BLOB_SHARED_KEY_CREDENTIALS=<ACCOUNT_NAME_1>:<SECRET_KEY_1>:<ACCOUNT_NAME_2>:<SECRET_KEY_2>:...
    
#### Specific property Files

```shell
.<account_name>.sbt-blob-token-credentials
.<account_name>.sbt-blob-shared-key-credentials
```

containing

```shell
accountKey=XXXXXX
```

### Custom Credentials

If the default credential providers are not enough for you you can specify your own CredentialsProvider using the `blobCredentialsProvider` SettingKey in your `build.sbt` file:

```scala
import com.valamis.sbt.azure.blob.resolver._
import com.microsoft.azure.storage.blob.ICredentials

...

blobCredentialsProvider := new AzureBlobStorageCredentialsProvider {

  override val credentialsType: CredentialsTypes.Value = CredentialsTypes.Token

  override def provide(accountName: String): Either[List[String], ICredentials] = {
    // returned value should be instance of
    // - Left[List[String]] with list of errors if there is a failure
    // - Right[ICredentials] if credentials are successfully provided
  }
}
```
where credentialsType should have one of these values:
```scala
object CredentialsTypes extends Enumeration {
  val Token: CredentialsTypes.Value = Value("token")
  val SharedKey: CredentialsTypes.Value = Value("shared-key")
  val Anon: CredentialsTypes.Value = Value("anon")
  val All: CredentialsTypes.Value = Value("all")
}
```

## Authors

lukaci (<a href="https://github.com/lukaci" rel="author">GitHub</a>)

Pavel Kornilov (<a href="https://github.com/pavel-kornilov" rel="author">GitHub</a>) - support for all types of credentials 

## License

[GNU General Public License, Version 3.0](https://www.gnu.org/licenses/gpl.txt)