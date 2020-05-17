package net.kozinaki.ulitkabot.docker

import java.util
import java.util.concurrent.ExecutionException

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.exception.DockerException
import com.github.dockerjava.api.model.{Container, Info}
import com.github.dockerjava.core.DockerClientBuilder

import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import net.kozinaki.ulitkabot.config.Prop._
import net.kozinaki.ulitkabot.exception.AnotherExecutionException;

class Docker {

  private val SLASH = "/"
  private val DDOT = ": "
  private var dockerClient: DockerClient = null
  private var containers: Map[String, Container] = null

  {
    dockerClient = DockerClientBuilder.getInstance(getDockerSocket()).build()
    //DockerClient dockerClient = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://localhost:2375").build();
    val info = dockerClient.infoCmd.exec
    System.out.println(info)
  }


  def getContainersName(): mutable.Buffer[String] = {
    setContainers(getNewContainers(getDockerClient))
    getContainers.values.map((container: Container) => getNameStatus(container)).collect{ case name: String => name }.toBuffer
  }

  @throws(classOf[AnotherExecutionException])
  def stopContainer(name: String): Unit = {
    getContainers.get(name).map((container: Container) => {
      try return dockerClient.stopContainerCmd(container.getId).exec
      catch {
        case e: Exception => {
          println(e)
          throw new AnotherExecutionException("Another task already executing now!")
        }
      }
    })
  }

  @throws(classOf[AnotherExecutionException])
  def startContainer(name: String): Unit = {
    getContainers.get(name).map((container: Container) => {
      try return dockerClient.startContainerCmd(container.getId).exec
      catch {
        case e: Exception => {
          println(e)
          throw new AnotherExecutionException("Another task already executing now!")
        }
      }
    })
  }

  def getNewContainers(dockerClient: DockerClient): Map[String, Container] = {
    dockerClient.listContainersCmd.withShowAll(true)
      .exec.asScala.filter((container: Container) => util.Arrays.stream(container.getNames).anyMatch((name: String) => name.contains(getContainerPattern())))
      .map[(String, Container)]((container: Container) => formattingName(container.getNames()(0)) -> container).toMap
  }

  protected def formattingName(containerName: String): String = {
    containerName.replace(SLASH, "")
  }

  protected def getNameStatus(container: Container): String = {
    formattingName(container.getNames()(0).concat(DDOT).concat(container.getState))
  }

  protected def getContainers: Map[String, Container] = {
    if (containers == null) {
      containers = new HashMap[String, Container]
    }
    containers
  }

  protected def setContainers(containers: Map[String, Container]): Unit = {
    this.containers = containers
  }

  protected def getDockerClient: DockerClient = dockerClient

}
