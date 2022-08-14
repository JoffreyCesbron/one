### User Guide

You can run the program with the Main object.
The arguments are:
- inputFilePath --> full path for the json file
- outputDirPath --> directory where to write the result

At the end of the execution the "outputDirPath" directory will contain the 4 files corresponding to 4 steps.

### Data-flow presentation

The aim is to take the supplier data which corresponds to a description of cars to sell with information on it (ex: mileage, year of first regulation)
and transform the data to enrich the database already existing on the website.
We cannot take and enrich the database directly as some attributes are not the same, it's structured differently, and also it can be written differently (not the same language, abbreviation, normalization etc...).
The aim of the data-flow is to execute the transformation so that data are prepared, and we can do the matching with the database.
Also we need to determine for every product if it is a new one, or existing one.
For a new one we add it to the database, for an existing one, we have to check if it can enrich the current database.


### Question 5
#### get a unique key

A specific id for the car that ensure that the car is unique would be definitely the best option, but it's probably not possible.
To have the name of the seller would help also. So we have to find another way

#### match cars with all information that we have

We are not sure the products are the same car, even if we try to match a product with all the information that
we have (if we had the city, mileage...).
It could be 2 different cars even if they have many characteristics in common but there might not be other options.

There is also a problem regarding how the data are writen in the supplier and in the target. For example the model
variant cannot be written the same, "RS6 Avant 5.0 V10 quattro" could be written:
- "RS6 quattro Avant 5.0 V10" (words not in the same orders)
- "RS6 Avt. quattro 5.0 V10" (abbreviation)
- "RS6 Avt. quattro 5 V10" (missing letters/words)

Ideas to make match on the variant:
- make a match even if the words are not in the same order
- some words could be written with an abbreviation, so if it does not match we could try against with the abbreviation (ex: Avant, Avt...)
- when there is no match we can enrich a mapping table when there is no match, or create new abbreviation so that next time it matches

#### calculate a score of similarity

Some solutions exist also for this kind problem (Hamming distance, Levenshtein distance...) to measure the similarity.
The idea would be to try a few algo to see the one that works better in our solution. We could do that on all attributes that we 
have to match 2 cars.

#### add other attributes and new lines

If we could determine that the models are the same at least (not the same existing car), we could enrich  some attributes (ex for condition there
is also Exposition model, for the color Anthracite).
If we can determine it's the same car we can add information specific to the car like the mileage.
If we see it's a new car we juste have to add it to the database with all the info that we have.

### Improvements

Find if there is some outliers, correct and/or put them in a reject table
Write in other tables the data that cannot be normalized
