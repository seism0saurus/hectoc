# Hectoc Repository

Hectocs are small mathematical riddles or challenges developed by [Yusnier Viera](https://en.wikipedia.org/wiki/Yusnier_Viera).
A german source can be found at [https://wir-rechnen.de/hectoc](https://wir-rechnen.de/hectoc).
Both sources are unrelated to this project.
This site is an OpenSource project from [seism0saurus](https://seism0saurus.de).

The Hectocs consist of six numbers.
The goal is to combine the 6 numbers to a total of 100.
You can use the mathematical operations + - * / ^ and the parenthesis ( ).
Numbers can be combined, but you have to use all 6 of them and are not allowed to change the order.

For example: 991347 can be solved with "99+1-(3+4-7)".

## Website
The deployed instance of the website and web application can be found under [hectoc.seism0saurus.de](https://hectoc.seism0saurus.de).

## Projects

This repository contains multiple subprojects that are grouped around the Hectoc theme.
The work in this repository is licensed under the MIT License. See [LICENSE](./LICENSE) for more information.

### Challenger

The Hectoc Challenger in the [webapp](./webapp) folder is a webapplication, that creates challenges and checks your answer.

### Library

The Hectoc library in the [shared](./shared) folder contains some Java classes to generate Hectocs
and check solutions with a modified version of a [Shunting Yard Algorithm](https://en.wikipedia.org/wiki/Shunting_yard_algorithm)
and stacks.

You can use that library, if you want to implement your own Java apps or tools for Hectocs.

### Bot

The Hectoc Bot in the [bot](./bot) folder is a Mastodon bot, that runs [@hourlyhectoc@botsin.space](https://botsin.space/@hourlyhectoc).
It posts a Hectoc every hour and can favorite correct answers. Wrong answers get a private commend.

### Bruteforcer

The Hectoc Bruteforcer in the [bruteforcer](./bruteforcer) is a commandline tool
to try all possible solutions for the [unsolved challenges](https://wir-rechnen.de/hectoc/hectocs-ungeloest-unsolved).

## Build

The projects are structured as multimodule maven project.
It should be sufficient to call `maven compile` in the parent directory.
To create executable jar files, call `maven package`

## Run
TBD...