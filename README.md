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

* The library is specialized on the primitive types rather than attempting to
  support all types in a single class hierarchy. `pythagoras.f` and
  `pythagoras.d` provide equivalent functionality using 32-bit and 64-bit
  floating point values throughout. `pythagoras.i` is specialized on int and
  contains none of the curved geometry classes.

* The need for defensive copying is minimized by the existence of read-only
  interfaces for each geometric primitive, which allow no mutation of the
  entity. Thus a consumer that requires only to read the attributes of, for
  example, a `Rectangle` can accept an `IRectangle` to indicate to the caller
  that it will not (and indeed cannot) mutate the supplied entity. Similarly, a
  read-only interface can be returned to a private internal field without fear
  that the recipient will mutate it and wreak havoc.

  Bear in mind that the interfaces have performance implications in extremely
  performance sensitive situations. So use them anyway and then profile your
  application to determine whether there are places where you need to sacrifice
  code clarity and safety to obtain higher performance.

* The library supports garbage creation avoidance (for applications which are
  sensitive to garbage collection pauses, like video games). For example, in
  cases where entities return a `Rectangle` containing their bounds, a
  corresponding method exists which accepts a `Rectangle` into which to write
  the bounds. Further work is needed here, as some of the implementation
  internals create garbage that could be avoided.

* The library attempts to minimize the size of its instances by avoiding the
  inclusion of any fields that are not essential to the function of a
  particular geometric entity. For example, no cached hash codes or cached
  computed bounds are maintained.

* Helper methods associated with a given geometric primitive are separated into
  a utility class for each primitive. For example line-related primitives are
  in a class named `Lines`.

## License

Pythagoras is released under the Apache License, Version 2.0 which can be found
in the `LICENSE` file and at http://www.apache.org/licenses/LICENSE-2.0 on the
web.

[API documentation]: http://samskivert.github.com/pythagoras/apidocs/overview-summary.html
[jar file]: http://repo2.maven.org/maven2/com/samskivert/pythagoras/1.0/pythagoras-1.0.jar
