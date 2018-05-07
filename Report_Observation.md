## Comparing top 5 results between Lucene and BM25 Retrieval Models


### Overall Observations:
1. The scores computed by both the models can be observed to be at very close proximation. 
   This inference confirms the fact that BM25 is the default similarity function used in Lucene.
2. Most of the search results have overlaps and they occur in pretty much the same rank order.
3. The non overlapping results from the top 5 search results can be observed to have overlaps in the top 10 results.


### Query-wise Observations:

```
Query1 - dark eclipse moon
--------------------------

Lucene
------
1 Q0 Lunar_eclipse 1 8.7907915 Lucene_Query_Parser
1 Q0 Solar_eclipse 2 8.331141 Lucene_Query_Parser
1 Q0 Magnitude_of_eclipse 3 8.133974 Lucene_Query_Parser
1 Q0 Magnitude_of_an_eclipse 4 8.096053 Lucene_Query_Parser
1 Q0 Lunation 5 8.095892 Lucene_Query_Parser

BM25
----
1 Q0 Lunar_eclipse 1 7.033810266098031 BM25
1 Q0 Solar_eclipse 2 6.656116904376261 BM25
1 Q0 Magnitude_of_eclipse 3 6.451662163922288 BM25
1 Q0 Magnitude_of_an_eclipse 4 6.449700964423167 BM25
1 Q0 New_moon 5 6.435051873091181 BM25

Overlaps
--------
The following documents get returned by both the retrieval models and in the same rank order
Lunar_eclipse
Solar_eclipse
Magnitude_of_eclipse
Magnitude_of_an_eclipse

```



```
Query2 - forecast models
------------------------

Lucene
------
2 Q0 Predictive_power 1 8.278679 Lucene_Query_Parser
2 Q0 Flooding 2 7.88317 Lucene_Query_Parser
2 Q0 Gravity_Recovery_and_Climate_Experiment 3 7.7773886 Lucene_Query_Parser
2 Q0 Skylab 4 6.117708 Lucene_Query_Parser
2 Q0 European_Network_of_Transmission_System_Operators_for_Electricity 5 5.7838736 Lucene_Query_Parser

BM25
----
2 Q0 Predictive_power 1 7.9722336606773485 BM25
2 Q0 Gravity_Recovery_and_Climate_Experiment 2 7.410091023708606 BM25
2 Q0 Flooding 3 7.371614598863967 BM25
2 Q0 Skylab 4 5.810988900669697 BM25
2 Q0 European_Network_of_Transmission_System_Operators_for_Electricity 5 5.74798692470651 BM25

Overlaps
--------
All of the search results overlap, only difference being their ranks.
```



```
Query3 - total eclipse solar
----------------------------

Lucene
------
3 Q0 Solar_eclipse 1 6.2753954 Lucene_Query_Parser
3 Q0 List_of_solar_eclipses_visible_from_the_United_Kingdom 2 6.2715535 Lucene_Query_Parser
3 Q0 Solar_eclipse_of_August_1,_2008 3 6.2348766 Lucene_Query_Parser
3 Q0 Solar_eclipse_of_July_16,_2186 4 6.191542 Lucene_Query_Parser
3 Q0 Solar_eclipse_of_August_18,_1868 5 6.190928 Lucene_Query_Parser

BM25
----
3 Q0 Solar_eclipse_of_August_1,_2008 1 2.871033308791238 BM25
3 Q0 List_of_solar_eclipses_visible_from_the_United_Kingdom 2 2.8670498225698657 BM25
3 Q0 Lunar_eclipse 3 2.8642406236971962 BM25
3 Q0 Solar_eclipse 4 2.8627224038187187 BM25
3 Q0 Solar_eclipse_of_August_21,_2017 5 2.850001398697228 BM25

Overlaps
--------
The following documents get returned by both the retrieval models
Solar_eclipse
List_of_solar_eclipses_visible_from_the_United_Kingdom
Solar_eclipse_of_August_1,_2008
```



```
Query4 - japan continental airline
----------------------------------

Lucene
------
4 Q0 Aeroflot 1 9.912504 Lucene_Query_Parser
4 Q0 East_China_Sea 2 7.175435 Lucene_Query_Parser
4 Q0 Continental_climate 3 6.7030673 Lucene_Query_Parser
4 Q0 Island 4 6.642952 Lucene_Query_Parser
4 Q0 Concorde 5 6.4737115 Lucene_Query_Parser

BM25
----
4 Q0 Aeroflot 1 9.671531061918158 BM25
4 Q0 East_China_Sea 2 6.477236400661848 BM25
4 Q0 Concorde 3 6.120897268848509 BM25
4 Q0 Solar_flare 4 6.042671333443715 BM25
4 Q0 Solar_flares 5 6.040031332630002 BM25

Overlaps
--------
The following documents get returned by both the retrieval models
Aeroflot
East_China_Sea
Concorde
```



```
Query5 - japan continental airlines
-----------------------------------

Lucene
------
5 Q0 Concorde 1 9.209212 Lucene_Query_Parser
5 Q0 Novosibirsk 2 9.011815 Lucene_Query_Parser
5 Q0 Aeroflot 3 8.550597 Lucene_Query_Parser
5 Q0 Japan 4 8.320739 Lucene_Query_Parser
5 Q0 Heathrow_Airport 5 8.216576 Lucene_Query_Parser

BM25
----
5 Q0 Concorde 1 8.821917896093872 BM25
5 Q0 Novosibirsk 2 8.313456785344972 BM25
5 Q0 Aeroflot 3 8.284483174906367 BM25
5 Q0 Heathrow_Airport 4 7.925163392461575 BM25
5 Q0 Japan 5 7.750350033741431 BM25

Overlaps
--------
All of the search results overlap, only difference being their ranks.
```



```
Query6 - solar eclipse fiction
------------------------------

Lucene
------
6 Q0 Solar_eclipses_in_fiction 1 10.2089405 Lucene_Query_Parser
6 Q0 Gravitational_shielding 2 7.860788 Lucene_Query_Parser
6 Q0 Halys_River 3 7.256646 Lucene_Query_Parser
6 Q0 Patrick_Moore 4 7.234945 Lucene_Query_Parser
6 Q0 Solar_eclipse 5 6.7766 Lucene_Query_Parser

BM25
----
6 Q0 Solar_eclipses_in_fiction 1 7.86792316121384 BM25
6 Q0 Patrick_Moore 2 7.011706307096775 BM25
6 Q0 Halys_River 3 6.930152764153093 BM25
6 Q0 Gravitational_shielding 4 6.294986031120854 BM25
6 Q0 Oblate_spheroid 5 5.0949466485756485 BM25

Overlaps
--------
The following documents get returned by both the retrieval models
Solar_eclipses_in_fiction
Gravitational_shielding
Halys_River
Patrick_Moore
```



```
Query7 - 2017 solar eclipse
---------------------------

Lucene
------
7 Q0 Solar_eclipses_in_fiction 1 4.6461864 Lucene_Query_Parser
7 Q0 Solar_eclipse 2 4.6324654 Lucene_Query_Parser
7 Q0 Solar_eclipse_of_January_15,_2010 3 4.6239204 Lucene_Query_Parser
7 Q0 Eclipse 4 4.6184015 Lucene_Query_Parser
7 Q0 Solar_eclipse_of_August_21,_2017 5 4.61743 Lucene_Query_Parser

BM25
----
7 Q0 Solar_eclipse_of_August_21,_2017 1 5.457341009180466 BM25
7 Q0 Solar_viewer 2 5.406213642582126 BM25
7 Q0 Eclipse_season 3 5.226249582156633 BM25
7 Q0 Baily's_beads 4 5.137561362137857 BM25
7 Q0 Diamond_ring_effect 5 5.13563510763794 BM25

Overlaps
--------
The following is the only overlap observed
Solar_eclipse_of_August_21,_2017
```



```
Query8 - total eclipse lyrics
-----------------------------

Lucene
------
8 Q0 Solar_eclipses_in_fiction 1 10.028784 Lucene_Query_Parser
8 Q0 Râmnicu_Vâlcea 2 9.849114 Lucene_Query_Parser
8 Q0 Earthshine 3 5.912819 Lucene_Query_Parser
8 Q0 List_of_solar_eclipses_visible_from_the_United_Kingdom 4 4.8684564 Lucene_Query_Parser
8 Q0 Solar_eclipse 5 4.848749 Lucene_Query_Parser

BM25
----
8 Q0 Râmnicu_Vâlcea 1 8.551894706672092 BM25
8 Q0 Solar_eclipses_in_fiction 2 8.189679435884266 BM25
8 Q0 Earthshine 3 5.911983109943902 BM25
8 Q0 Compact_Disc 4 3.422193037284137 BM25
8 Q0 Solar_eclipse_of_August_1,_2008 5 2.987613370426416 BM25

Overlaps
--------
The following documents get returned by both the retrieval models
Solar_eclipses_in_fiction
Râmnicu_Vâlcea
Earthshine
```



```
Query9 - nordic marine animals
------------------------------

Lucene
------
9 Q0 Scandinavian_Peninsula 1 10.330206 Lucene_Query_Parser
9 Q0 Neoproterozoic 2 9.604613 Lucene_Query_Parser
9 Q0 Extinction_event 3 7.5030203 Lucene_Query_Parser
9 Q0 Synchronous_grid_of_Continental_Europe 4 7.288756 Lucene_Query_Parser
9 Q0 Cretaceous–Tertiary_extinction_event 5 7.1850533 Lucene_Query_Parser

BM25
----
9 Q0 Scandinavian_Peninsula 1 9.74728194186753 BM25
9 Q0 Neoproterozoic 2 9.199399213271576 BM25
9 Q0 Synchronous_grid_of_Continental_Europe 3 7.2271282926113996 BM25
9 Q0 Extinction_event 4 6.887809812563447 BM25
9 Q0 Cretaceous–Tertiary_extinction_event 5 6.62222691395957 BM25

Overlaps
--------
All of the search results overlap, only difference being their ranks.

```



```
Query10 - volcanic eruptions tornadoes eruption tornado
-------------------------------------------------------

Lucene
------
10 Q0 Natural_phenomenon 1 27.496407 Lucene_Query_Parser
10 Q0 Types_of_volcanic_eruptions 2 17.202219 Lucene_Query_Parser
10 Q0 Volcano 3 16.625614 Lucene_Query_Parser
10 Q0 Volcanism 4 15.806179 Lucene_Query_Parser
10 Q0 Basalt 5 15.02179 Lucene_Query_Parser

BM25
----
10 Q0 Natural_phenomenon 1 26.95827757720252 BM25
10 Q0 Types_of_volcanic_eruptions 2 16.6570281735604 BM25
10 Q0 Volcano 3 16.094422872955704 BM25
10 Q0 Volcanism 4 15.279493518897388 BM25
10 Q0 Basalt 5 14.541258907970988 BM25

Overlaps
--------
All of the search results overlap, also occuring in the same ranking.
```
