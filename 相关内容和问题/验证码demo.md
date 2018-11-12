```java

//禁止浏览器缓存随机图片
response.setDateHeader("Expires",-1);
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");

//创建一张图片，宽高的单位为像素，第三个参数需要传入图片的类型
BufferedImage image = new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);

//获取Graphics2D对象，是在原有图片的上面画图
Graphics2D gra = image.createGraphics();
//向画板上画图必须先设置画笔，传入一个Color对象，可以使用Color类中的常量
gra.setColor(Color.white);

//填充一个矩形区域
gra.fillRect(0,0,image.getWidth(),image.getHeight());

//向图片中添加字符串
List<Integer> list = new ArrayList<>();
Random random = new Random();
for (int i = 0; i < 4; i++) {
    list.add(random.nextInt(10));
}
System.out.println(list.toString());
gra.setFont(new Font(null,Font.BOLD,60));
Color[] colors = new Color[]{Color.red,Color.cyan,Color.black,Color.green,Color.MAGENTA};
for (int i = 0; i < list.size(); i++) {
    //产生随机颜色和位置
    gra.setColor(colors[random.nextInt(colors.length)]);
    gra.drawString(list.get(i)+"" ,i*40,50+(random.nextInt(21)-10));
}

//划横线
for (int i = 0; i < 2; i++) {
    gra.drawLine(0,random.nextInt(101),200,random.nextInt(101));
}

ServletOutputStream outputStream = response.getOutputStream();
//ImageIO是一个工具类,可以把指定的图片添加到输出流中
ImageIO.write(image,"jpg",outputStream);
       
//把验证码放入session中
HttpSession session = request.getSession();
session.setAttribute("code",""+list.get(0)+list.get(1)+list.get(2)+list.get(3));
```