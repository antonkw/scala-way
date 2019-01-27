# scala-way

From: http://fasihkhatib.com/2017/06/28/scalaz-equals/

**Equal**


Scalaz `===` lets you check for equality in a type-safe way. More often than not, this is what you need. Trying to compare values of dissimilar types is usually not needed. Using === ensures that such comparisons lead to errors at compile-time instead of waiting for them to surface at run-time. 

`1 === "str"` will not compile and `1 == "str"` returns `false`

NOTE: do not use `\==` as replacement for `!=`. It affects order of expression evaluation (aspect of scalac, please do not try to understand this).
```
1 /== 2 && false
 error: value && is not a member of Int
```
`2 && false` will try to evaluate first.

Summary: `=/=` and `===` are type safe alternative to basic `!=` and `==`.
`≟` and `≠` are just sugar, I see one reason to use them: scalastic provide `===` as operator for asserts, so we can use `≟` to verify something in tests.


`assert_===` returns Unit and throws exception in case of mismatch.


For custom types you need to define implicit:
```
case class StringDecorator(s: String)
implicit val StringDecoratorEqual: Equal[StringDecorator] = Equal.equalA[StringDecorator]
```

**Ordering**

Allows you to do stuff like Option compare (None will be smaller than any Some):
```
val (one, two) = (1.some, 2.some)
if (one < two) ...
```

In practice to use with "usual" types and preserve Ordering type-safe semantic you need to use non-default comparison operators.

To evaluate `1 <= 2.0` default operator will be used. And `1 lte 2.0` will produce compilation error.
`?|?` returns actual ordering (EQ/LT/GT) `assert(1.0 ?|? 2.0 == Ordering.LT)`