# Hectoc Challenger

A webapplication for generating and validation of hectoc riddles.

Hectocs are mathematical challenges developed by [Yusnier Viera](https://en.wikipedia.org/wiki/Yusnier_Viera). More
infos at [https://wir-rechnen.de/hectoc](https://wir-rechnen.de/hectoc).

Both sources are unrelated to this project.

The goal is to combine the 6 numbers to a total of 100. You can use the mathematical operations + - * / ^ and the
parenthesis () Numbers can be combined but you have to use all 6 of them.

For example: 991347 can be solved with "99+1-(3+4-7)".

This site is a private project from seism0saurus

## Development

You can start the main class in [HectocApplication.java](/src/main/java/de/seism0saurus/hectoc/HectocApplication.java)
with the IDE of your choice.

The frontend development server can be starten with `ng serve` from inside the [angular](/angular/) folder, after you
have installed the dependencies with `npm install`.

Some paths or names are hard coded at the moment, so you will have to fix these.