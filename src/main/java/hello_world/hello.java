package hello_world;

/**
 * Created by evan on 7/17/17.
 */
public class hello {
    public static void main(String args[]) {
        System.out.print("hello world/n");
        hello thingy = new hello();
        System.out.println(thingy.method(5));
    }

    public int method(int thing) {
        return thing*2;
    }
}
