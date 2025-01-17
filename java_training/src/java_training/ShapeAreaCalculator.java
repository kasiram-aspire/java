package java_training;
abstract class Shape
{
	abstract double getArea();
}
class Circle extends Shape
{
	public int radius;

	public Circle(int radius) {
		this.radius = radius;
	}
	public double getArea()
	{
		return(Math.PI*Math.sqrt(radius));
	}
	public int getRadius() {
		return radius;
	}
	
	
}
class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
class switchcalculator
{
	public double calc(Shape shape)
	{
		double value=switch(shape)
				{
	             	case Circle c->c.getArea();
	             	case Rectangle r->r.getArea();
		            default -> throw new IllegalArgumentException("Unexpected value: ");
				};
				return value;
	}
}

public class ShapeAreaCalculator {

	public static void main(String[] args) {
		Shape circle=new Circle(4);
		Shape rectangle=new Rectangle(4,3);
		 switchcalculator swc=new switchcalculator();
		 System.out.println(swc.calc(circle));
		 System.out.println(swc.calc(rectangle));
		 
	}

}
