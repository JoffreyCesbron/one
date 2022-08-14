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
The aim data-flow is to execute the transformation so that at the end we get data that can be loaded directly in the database.

### Question 5

#### add other attributes

We can enrich the data if the "make", "model" "variant" are the same for a product. For example, we could enrich the
"Consumption" in our case. We can also enrich the attributes that have been normalized (ex for condition there
is also Exposition model, for the color Anthracite).

#### get an unique key

The idea would be to get the name of the seller to be sure it's the same car, or a specific idea for the car that ensure that the car is unique, but it might not be possible...

#### match car with all information that we have

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

Use ML to train and predict

### Improvements

Find if there is some outliers, correct and/or put them in a reject table
Write in other tables the data that cannot be normalized



# one
