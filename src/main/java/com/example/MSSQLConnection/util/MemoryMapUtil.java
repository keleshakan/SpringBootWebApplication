package com.example.MSSQLConnection.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class MemoryMapUtil {

    // The next line shows 10 MB as the max value of the count
    private static int count = 10485760;

    public static void writeAndReadMemory(){
        // Try block to check for exceptions
        try (RandomAccessFile sc
                     = new RandomAccessFile("text.txt", "rw")) {

            Scanner s = new Scanner(System.in);

            String input = "keleshakan";

            // By “memory-mapped” file, our application can use memory outside JVM memory
            MappedByteBuffer out = sc.getChannel().map(
                    FileChannel.MapMode.READ_WRITE, 0, 10);

            for (int i = 0; i < 10; i++) {
                System.out.println((out.put((byte)(input.charAt(i)))));
            }

            System.out.println("Writing to Memory is complete");

            for (int i = 0; i < 10; i++) {
                System.out.println((char)out.get(i));
            }

            System.out.println(
                    "Reading from Memory is complete");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
