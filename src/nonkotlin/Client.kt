package nonkotlin

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket;
import java.util.*

fun main() {

    val sock = Socket(
            InetAddress.getByName("192.168.0.109"),
            23456)


    val input = DataInputStream(sock.getInputStream())
    val output = DataOutputStream(sock.getOutputStream())
    val sc = Scanner(System.`in`)

    val outputThread = Thread {
        while (true) {
            output.writeUTF(sc.nextLine())
        }
    }.start()

    val inputThread = Thread {
        while (true) {
            println(input.readUTF())
        }
    }.start()
}