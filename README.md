# DiscrepancyFinder

Description: automatically read two excel files, which have different format and signs but represent the same meaning, and report the discrepancies

Background: our company mainly uses excel files to record attendance records and reports such data to both HR department and Airport Authority, but due to the division of works, there are always discrepancies between those excel files. Since comparing all data and finding discrepancies manually is very time-consuming, I then wrote this Java program to improve overall work efficiency.

Technologies used:
JavaFX: FXML, multi-threading
Library: Apache POI, HyperSQL (HSQLDB)
Algorithm: Quick Sort, Binary Search

How it works:
The program creates common data model to store data which are read from excel files since different excel file has different format and sign.

The program let user pre-define file settings (e.g. start row and end row to read…etc.) and mappings (e.g. L=R, SL=M). All pre-defined file settings and mappings are stored into HyperSQL database.

The program reads the two excel files and store the data to array list of data model with aligned sign according to pre-defined mappings.

The program then opens an independent thread to perform quick sort and binary search to find the discrepancies between two array lists of data model, and finally output a discrepancies report on the main screen.
