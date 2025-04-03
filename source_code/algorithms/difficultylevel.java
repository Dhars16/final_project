package com.spring.graph.api.algorithms;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class difficultylevel {
	
	  public static void main(String[] args)  {
	String difficultyTargetHex = "0000000000000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

    // Convert the difficulty target to a BigInteger
    BigInteger difficultyTarget = new BigInteger(difficultyTargetHex, 16);

    // Calculate the difficulty level
    double difficultyLevel = calculateDifficultyLevel(difficultyTarget);

    // Print the result
    System.out.println("Difficulty Level: " + difficultyLevel);
	  }

public static double calculateDifficultyLevel(BigInteger difficultyTarget) {
    // Define a reference difficulty target (for example, the initial target)
    BigInteger referenceDifficultyTarget = new BigInteger("00000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);

    // Calculate the difficulty level as a ratio of the reference difficulty target
    double difficultyRatio = referenceDifficultyTarget.doubleValue() / difficultyTarget.doubleValue();

    // Adjust the difficulty level as needed (this is a simple example)
    return difficultyRatio;
}}