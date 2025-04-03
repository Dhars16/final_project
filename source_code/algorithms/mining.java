package com.spring.graph.api.algorithms;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class mining {

	
	
	
	

    private class Block {
        private int index;
        private String previousHash;
        private String data;
        private long timestamp;
        private String hash;
        private int nonce;

        public Block(int index, String previousHash, String data) {
            this.index = index;
            this.previousHash = previousHash;
            this.data = data;
            this.timestamp = new Date().getTime();
            this.hash = calculateHash();
            this.nonce = 0;
        }

        public String calculateHash() {
            return applySha256(
                index + previousHash + timestamp + data + nonce
            );
        }

        public void mineBlock(int difficulty) {
            String target = new String(new char[difficulty]).replace('\0', '0');

            while (!hash.substring(0, difficulty).equals(target)) {
                nonce++;
                hash = calculateHash();
            }
         
            
            
            
            
            System.out.println("Block mined: " + hash);
        }
    }

    private List<Block> blockchain;
    private int difficulty;

    public mining(int difficulty) {
        this.blockchain = new ArrayList<>();
        this.difficulty = difficulty;
        // Genesis block
        addBlock("Genesis Block");
    }

    public void addBlock(String data) {
        int index = blockchain.size();
        Block previousBlock = (index > 0) ? blockchain.get(index - 1) : null;
        String previousHash = (previousBlock != null) ? previousBlock.hash : "0";

        Block newBlock = new Block(index, previousHash, data);
        newBlock.mineBlock(difficulty);

        blockchain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Block hash mismatch at index " + i);
                return false;
            }

            if (!currentBlock.previousHash.equals(previousBlock.hash)) {
                System.out.println("Previous hash mismatch at index " + i);
                return false;
            }
        }

        System.out.println("Blockchain is valid.");
        return true;
    }

    private String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int initialDifficulty = 1;
        mining blockchain = new mining(initialDifficulty);

        // Adding blocks to the blockchain
        blockchain.addBlock("Transaction 1");
      
 
        // Validating the blockchain
        blockchain.isChainValid();
    }
}