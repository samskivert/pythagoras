# Pythagoras

Pythagoras is a collection of geometry classes, the implementations of which
were adapted from the Apache Harmony project. The Pythagoras library aims to
provide performant, portable geometry routines for projects that cannot make
use of `java.awt.geom` (for example for use in GWT projects or Android
projects).

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
  64-bit floating point values throughout.

* A version of the library exists that is specialized on `int` values:
  `pythagoras.i`. It does not provide many of the more complex geometric
  shapes, and is targeted toward performing "pixel geometry" where lines do
  not have zero width, but rather have single pixel width.

## License

Pythagoras is released under the Apache License, Version 2.0 which can be found
in the `LICENSE` file and at http://www.apache.org/licenses/LICENSE-2.0 on the
web.
