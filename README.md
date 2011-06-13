# Pythagoras

Pythagoras is a collection of geometry classes, the implementations of which
were adapted from the Apache Harmony project. The Pythagoras library aims to
provide performant, portable geometry routines for projects that cannot make
use of `java.awt.geom` (for example for use in GWT projects or Android
projects).

* [API documentation] is available.

* Pythagoras can be obtained via Maven Central: `com.samskivert:pythagoras:1.0`.
  Or you can download the pre-built [jar file].

## Design

Some restructuring of the `java.awt.geom` classes was undertaken to meet
certain design goals.

* The library supports garbage creation avoidance for applications which are
  sensitive to garbage collection pauses, like video games. For example, in
  cases where entities return a `Rectangle` containing their bounds, a
  corresponding method exists which accepts a `Rectangle` into which to write
  the bounds.

* The library attempts to minimize the size of its instances, by avoiding the
  inclusion of any fields that are not essential to the function of a
  particular geometry entity. Thus no cached hash codes or cached computed
  bounds are maintained.

* Helper methods associated with a given geometric primitive are separated into
  a separate utility class for that primitive. For example line-related
  primitives are in a class named `Lines`.

* The library is specialized on the primitive types rather than attempting to
  support all types in a single class hierarchy. `pythagoras.f` and
  `pythagoras.d` provide essentially the same functionality using 32-bit and
  64-bit floating point values throughout. `pythagoras.i` is specialized on int
  and contains none of the curved geometry classes. It is targeted toward
  applications that need "pixel geometry".

## License

Pythagoras is released under the Apache License, Version 2.0 which can be found
in the `LICENSE` file and at http://www.apache.org/licenses/LICENSE-2.0 on the
web.

[API documentation]: http://samskivert.github.com/pythagoras/apidocs/overview-summary.html
[jar file]: http://repo2.maven.org/maven2/com/samskivert/pythagoras/1.0/pythagoras-1.0.jar
