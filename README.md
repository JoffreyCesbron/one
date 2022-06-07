# lemma

The goal of the app is to take in input a csv file with one word by line and write back another csv
file with the input words and their lemmas (if existings).

## Using the App

### 1 - create the directories with the dictionaries you need

Create a folder, for exemple "dictionaries". Inside this folder create a folder for each language you want to use with the app.
Inside any language folder you should place the 2 files (.aff and .dic) prefixed with the language name. 

So if you want to use the french dictionary you can create a directory "dictionaries" and directory "french" in it.
Inside the "french" directory you should place the 2 files "french.dic" and "french.aff"

### 2 - arguments

To launch the job you have to launch it with the 4 arguments: 
-i "absolute path to the input.csv file"
-o "absolute path to the directory you want to write the result"
-l "the language you want to use"
-d "absolute path to the directory where all the dictionary are stored"

Ex:
-i /home/user/Desktop/input.csv
-o /home/user/Desktop/output
-l french
-d /home/user/Desktop/dictionaries

### 3 - launch

The input.csv file should contain at least one word. It there are some spaces before and after they will be removed.
Launch the job and get the result in the directory you specified.
The words that do not exist will be present with the word and "-> does not exist"

## technical choices

To avoid any modifications on the code in case of a new dictionary the most simple was to create a directory external to the app that contains the files needed
for the application to work.
In case of a new dictionary you juste have to put the files in the directory as explained above. If you want to use this dictionary you 
have to specify in the args that you want to use this one.
If the dictionaries are on the S3 you can imagine a playbook that automatically put the dictionnaries in the right with the good name.

## hesitations

Do we want the output sorted in the same order than the input ?
Do we want to remove the word that does not exist in the output (words with ""), do we create a reject table with these words ?

## improvements

Catch the exception if the input file is empty
Add an integration to test all the application
Performance test on all the application
Find the files with the extension and not the full name to reduce the mistakes
Add a function in Lemmatize that takes a dataframe in input and returns the dataframe with the lemmas (to have less code in
the main)
