LOAD DATA 
INFILE *
APPEND INTO TABLE IDOCFMT FIELDS TERMINATED BY '#' 
TRAILING NULLCOLS
(
ID CHAR(10), 
NAME CHAR(32), 
ARCHID CHAR(10),
TYPE CHAR (10),
SUBTYPE CHAR (10),
clob_filename FILLER CHAR(100), 
DATA LOBFILE(clob_filename) TERMINATED BY EOF, 
REMARKS CHAR(254),
ACCESSTYPE CHAR(10), 
ACSID CHAR(10), 
CRTRID CHAR(10),
CRTNDATE DATE "DD/MM/YYYY HH24:MI:SS" ":crtndate", 
UPDRID CHAR(10), 
UPDDATE DATE "DD/MM/YYYY HH24:MI:SS" ":upddate"
)
BEGINDATA
7#Predeterminado#1#2#3#SIGM_SIGM_SIGM-04-LOADER_23.DAT##1#2147483646#0#22/05/2013 17:39:44##
8#Predeterminado#1#1#3#SIGM_SIGM_SIGM-04-LOADER_08.DAT##1#2147483646#0#22/05/2013 17:39:44##
9#Predeterminado#1#3#3#SIGM_SIGM_SIGM-04-LOADER_07.DAT##1#2147483646#0#22/05/2013 17:39:44##
10#Predeterminado#2#1#3#SIGM_SIGM_SIGM-04-LOADER_01.DAT##1#2147483646#0#22/05/2013 17:39:44##
11#Predeterminado#2#2#3#SIGM_SIGM_SIGM-04-LOADER_30.DAT##1#2147483646#0#22/05/2013 17:39:44##
12#Predeterminado#2#3#3#SIGM_SIGM_SIGM-04-LOADER_06.DAT##1#2147483646#0#22/05/2013 17:39:44##
13#Predeterminado#3#1#3#SIGM_SIGM_SIGM-04-LOADER_03.DAT##1#2147483646#0#22/05/2013 17:39:44##
14#Predeterminado#3#2#3#SIGM_SIGM_SIGM-04-LOADER_31.DAT##1#2147483646#0#22/05/2013 17:39:44##
15#Predeterminado#3#3#3#SIGM_SIGM_SIGM-04-LOADER_04.DAT##1#2147483646#0#22/05/2013 17:39:44##
16#Predeterminado#4#1#3#SIGM_SIGM_SIGM-04-LOADER_05.DAT##1#2147483646#0#22/05/2013 17:39:44##
17#Predeterminado#4#2#3#SIGM_SIGM_SIGM-04-LOADER_32.DAT##1#2147483646#0#22/05/2013 17:39:44##
18#Predeterminado#4#3#3#SIGM_SIGM_SIGM-04-LOADER_02.DAT##1#2147483646#0#22/05/2013 17:39:44##
21#PorDefectoSicres#5#1#3#SIGM_SIGM_SIGM-04-LOADER_09.DAT##1#2147483646#0#22/05/2013 17:39:44##
22#PorDefectoSicres#5#2#3#SIGM_SIGM_SIGM-04-LOADER_33.DAT##1#2147483646#0#22/05/2013 17:39:44##
23#PorDefectoSicres#5#3#3#SIGM_SIGM_SIGM-04-LOADER_10.DAT##1#2147483646#0#22/05/2013 17:39:44##
24#PorDefectoSicres.3#1#1#3#SIGM_SIGM_SIGM-04-LOADER_21.DAT##1#2147483646#0#14/01/2014 17:44:00##
25#PorDefectoSicres.3#1#2#3#SIGM_SIGM_SIGM-04-LOADER_34.DAT##1#2147483646#0#14/01/2014 17:44:00##
26#PorDefectoSicres.3#1#3#3#SIGM_SIGM_SIGM-04-LOADER_22.DAT##1#2147483646#0#17/12/2013 15:15:23##
27#PorDefectoSicres.3#5#1#3#SIGM_SIGM_SIGM-04-LOADER_13.DAT##1#2147483646#0#10/06/2014 17:24:13##
28#PorDefectoSicres.3#5#2#3#SIGM_SIGM_SIGM-04-LOADER_35.DAT##1#2147483646#0#10/06/2014 17:24:13##
29#PorDefectoSicres.3#5#3#3#SIGM_SIGM_SIGM-04-LOADER_14.DAT##1#2147483646#0#10/06/2014 17:24:13##
30#PorDefectoSicres.3#6#1#3#SIGM_SIGM_SIGM-04-LOADER_15.DAT##1#2147483646#0#10/06/2014 17:24:13##
31#PorDefectoSicres.3#6#2#3#SIGM_SIGM_SIGM-04-LOADER_36.DAT##1#2147483646#0#10/06/2014 17:24:13##
32#PorDefectoSicres.3#6#3#3#SIGM_SIGM_SIGM-04-LOADER_16.DAT##1#2147483646#0#10/06/2014 17:24:13##
36#PorDefectoSicres.3#7#1#3#SIGM_SIGM_SIGM-04-LOADER_17.DAT##1#2147483646#0#10/06/2014 17:24:13##
37#PorDefectoSicres.3#7#2#3#SIGM_SIGM_SIGM-04-LOADER_37.DAT##1#2147483646#0#07/01/2014 15:59:44##
38#PorDefectoSicres.3#7#3#3#SIGM_SIGM_SIGM-04-LOADER_18.DAT##1#2147483646#0#07/01/2014 15:59:44##
39#PorDefectoSicres.3#2#1#3#SIGM_SIGM_SIGM-04-LOADER_19.DAT##1#2147483646#0#07/01/2014 15:59:44##
40#PorDefectoSicres.3#2#2#3#SIGM_SIGM_SIGM-04-LOADER_38.DAT##1#2147483646#0#07/01/2014 15:59:44##
41#PorDefectoSicres.3#2#3#3#SIGM_SIGM_SIGM-04-LOADER_20.DAT##1#2147483646#0#14/01/2014 17:44:00##
45#PorDefectoSicres.3#8#1#3#SIGM_SIGM_SIGM-04-LOADER_11.DAT##1#2147483646#0#10/06/2014 17:24:13##
46#PorDefectoSicres.3#8#2#3#SIGM_SIGM_SIGM-04-LOADER_39.DAT##1#2147483646#0#10/06/2014 17:24:13##
47#PorDefectoSicres.3#8#3#3#SIGM_SIGM_SIGM-04-LOADER_12.DAT##1#2147483646#0#10/06/2014 17:24:13##
48#PorDefectoSicres.3#9#1#3#SIGM_SIGM_SIGM-04-LOADER_24.DAT##1#2147483646#0#10/06/2014 17:24:13##
49#PorDefectoSicres.3#9#2#3#SIGM_SIGM_SIGM-04-LOADER_25.DAT##1#2147483646#0#10/06/2014 17:24:13##
50#PorDefectoSicres.3#9#3#3#SIGM_SIGM_SIGM-04-LOADER_26.DAT##1#2147483646#0#10/06/2014 17:24:13##
51#PorDefectoSicres.3#11#1#3#SIGM_SIGM_SIGM-04-LOADER_27.DAT##1#2147483646#0#10/06/2014 17:24:13##
52#PorDefectoSicres.3#11#2#3#SIGM_SIGM_SIGM-04-LOADER_28.DAT##1#2147483646#0#10/06/2014 17:24:13##
53#PorDefectoSicres.3#11#3#3#SIGM_SIGM_SIGM-04-LOADER_29.DAT##1#2147483646#0#10/06/2014 17:24:13##