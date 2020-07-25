package com.github.yangguang19.juc.lock.Lock8;


class Phone {

    public synchronized void sendSMS() throws Exception {

        System.out.println("------发短信");
    }

    public synchronized void sendEmail() throws Exception {
        System.out.println("------发邮件");
    }

    public void getHello() {
        System.out.println("------getHello");
    }

}

/**
 * @Description: 8锁
 * <p>
 * 1 标准访问，先打印短信还是邮件
 * 2 停4秒在短信方法内，先打印短信还是邮件
 * 3 新增普通的hello方法，是先打短信还是hello
 * 4 现在有两部手机，先打印短信还是邮件
 * 5 两个静态同步方法，1部手机，先打印短信还是邮件
 * 6 两个静态同步方法，2部手机，先打印短信还是邮件
 * 7 1个静态同步方法,1个普通同步方法，1部手机，先打印短信还是邮件
 * 8 1个静态同步方法,1个普通同步方法，2部手机，先打印短信还是邮件
 * ---------------------------------
 */
public class Lock8 {
    public static void main(String[] args) throws Exception {

        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();

//        Thread.sleep(1000);

        new Thread(() -> {
            try {
//                phone.sendEmail();
                phone.getHello();
                //phone2.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();
    }
}


