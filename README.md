# SqlQueryBuilder
This project is based on building query table by giving sql query. In the first line of input, you will be given number of test cases T​. In every test case, the first line will contain the number of tables nT.​Then nT tables will be described. The first line of each table will contain the “table name”.​In the next line you will get two integers nC and nD where nC means number of columns of this table and nD means number of records this table contains. You can assume that all the table and column names will contain only small english letters, digits and underscore sign (_). You can also assume that the first column of every table will be the primary key of that table. In the next line you will be provided the column names separated by space. Next nD lines will contain nC space separated numeric data (di) of the table according to the column names. After all the table information, you will be provided the number of queries nQ you need to apply on the tables provided for this test case. Each query will contain four lines of input and you can assume that every query will be one of the sample provided in the problem. Also, there will be a blank link after each query. You can assume all the table column will contain data and every query is legal. For every test case you need to print “Test: d”​, where d is the test case number. For each query print the result of JOIN operation after printing the column names first. You should print only the columns given in the query and in the appropriate order. If asterisk sign (*) is provided in the query, you need to print all the column names according to the input provided. The rows should be ordered lexicographically. Print blank line after the answer for each query. See the example for more information.




##QUERY FORMAT 1##<br />
SELECT *<br />
FROM first_table_name<br />
JOIN second_table_name<br />
ON first_table_name.a name of the column from the first table = second_table_name.a<br />
name of the column of the second table<br />
##QUERY EXAMPLE##
SELECT *<br />
FROM table_a<br />
JOIN table_b<br />
ON table_a.column_a = table_b.column_b<br />


##QUERY FORMAT 2##<br />
SELECT *<br />
FROM first_table_name first_table_short_name<br />
JOIN second_table_name second_table_short_name<br />
ON first_table_short_name.column_name = second_table_short_name.column_name<br />
##QUERY EXAMPLE##
SELECT *<br />
FROM table_a ta<br />
JOIN table_b tb<br />
ON ta.column_a = tb.column_b<br />


##QUERY FORMAT 3##<br />
SELECT table_short_name.column_name, table_short_name.column_name, ...<br />
FROM first_table_name first_table_short_name<br />
JOIN second_table_name second_table_short_name<br />
ON first_table_short_name.column_name = second_table_short_name.column_name<br />

##QUERY EXAMPLE##
SELECT ta.column_a1, ta.column_a2, ta.column_a3, tb.column_b1, tb.column_b2<br />
FROM table_a ta<br />
JOIN table_b tb<br />
ON ta.column_a0 = tb.column_b0<br />


Sample Input:<br />

1<br />
2<br />
table_a<br />
3 3<br />
id_a a1 a2<br />
1 2 3<br />
2 4 5<br />
3 6 7<br />
table_b<br />
3 3<br />
id_b b1 b2<br />
1 2 9<br />
2 10 5<br />
3 12 7<br />
3<br />
SELECT *<br />
FROM table_a<br />
JOIN table_b<br />
ON table_a.a1 = table_b.b1<br />
SELECT *<br />
FROM table_a ta<br />
JOIN table_b tb<br />
ON ta.a2 = tb.b2<br />
SELECT ta.a1, ta.a2, tb.b1<br />
FROM table_a ta<br />
JOIN table_b tb<br />
ON ta.a2 = tb.b2<br />



Sample Output:<br />

Test: 1<br />
id_a a1 a2 id_b b1 b2<br />
1 2 3 1 2 9<br />
id_a a1 a2 id_b b1 b2<br />
2 4 5 2 10 5<br />
3 6 7 3 12 7<br />
a1 a2 b1<br />
4 5 10<br />
6 7 12





