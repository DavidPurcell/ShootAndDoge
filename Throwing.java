public class Throwing {
static void baz() throws Exception {
throw new Exception();
}
static void bar() throws Throwable {
try {
baz();
} catch (Exception e) {
System.out.println("A");
} finally {
System.out.println("B");
}
throw new Exception("C");
}
static void foo() throws Throwable {
bar();
System.out.println("D");
}
public static void main(String[] args) {
try {
foo();
} catch (Throwable t) {
System.out.println(t.getMessage());
}
System.out.println("E");
}
}