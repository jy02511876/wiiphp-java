package Object;

/**
 * 万物皆对象，
 * 每一个对象都有自己的属性，比如颜色，款式，尺寸，型号，品牌
 * 每一个对象都有自己的方法，方法中总有几个是最主要的方法，还有一些次要的方法，
 * 比如自行车的主要方法是用来骑的，
 * 数据库的对象，主要是用来查询的，当然还有数据库连接，数据库关闭的方法，这些就是次要的
 * @author zhoubin
 *
 */
public class God {
	public static void main(String[] args){
		Road renMinLu = new Road("人民路");
		System.out.println(renMinLu.things());
	}
}
