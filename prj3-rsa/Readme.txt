Paul Nguyen
Dhrumin Patel
University of Illinois in Chicago
March/15/2016
Project 3: RSA Encryption/Decryption

***RSA Encryption/Decryption***
This program provides a graphic user interface which creates private and public key for the user,
takes a message and perform blocking and unblocking operations, and performs encryption and decryption
on the message.

Depending on the length of the message. the key value of n must have a longer length,
in order to properly calculate. This program will take a list of prime numbers from
"primeNumbers.rsc" and randomly choice the prime number if user decides to input "random"
for that key. We also will allow the user to input their own prime numbers. When we block
a file, the output of that file will be in format of "name".bf. When we encrypted a file, 
the output of that file will be in format of "name".ef. The decrypted function will
attempt to return the original .txt file, with the proper keys.