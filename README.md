Provide an excel spreadsheet with data and we will give you what 
we think on how you can design the database. This program scans your 
whole excel spreadsheet looking for things like:
1.	If a column Heading ends with (s). i.e. Author(s), then we know 
	it is a many to many relationship
2.	If a column Heading is in its plural form then 2 rules can be applied
	a.	If the variable is an integer then it might mean a count. i.e. number_of_books
	b.	Else it is many-to-many i.e. Authors
3.	If we see repeating values, we split the table.
4.	More to come

Example spreadsheet provided
To Run Excel t1.xls
==============