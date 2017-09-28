package com.wb.framework.commonUtil.encrypt;

import java.util.Arrays;

import com.wb.framework.commonUtil.calendar.DataChange;

/*************************************************
 * md5 类实现了RSA Data Security, Inc.在提交给IETF 的RFC1321中的MD5 message-digest 算法。
 *************************************************/
public class MD5
{
    String hex_chr = "0123456789abcdef";
    
    private byte[] key;
    
    public MD5()
    {
        // TODO Auto-generated constructor stub
        this.key = new byte[16];
        this.key[0] = (byte)0x11;
        this.key[1] = (byte)0x22;
        this.key[2] = (byte)0x33;
        this.key[3] = (byte)0x44;
        this.key[4] = (byte)0x55;
        this.key[5] = (byte)0x66;
        this.key[6] = (byte)0x77;
        this.key[7] = (byte)0x88;
        this.key[8] = (byte)0x99;
        this.key[9] = (byte)0x00;
        this.key[10] = (byte)0xaa;
        this.key[11] = (byte)0xbb;
        this.key[12] = (byte)0xcc;
        this.key[13] = (byte)0xdd;
        this.key[14] = (byte)0xee;
        this.key[15] = (byte)0xff;
    }
    
    public MD5(byte[] key)
    {
        this.key = key;
    }
    
    public String hex(byte[] arr)
    {
        String str = "";
        for (int i = 0; i < arr.length; i++)
            str = str + hex_chr.charAt((arr[i] >> 4) & 0x0F) + hex_chr.charAt(arr[i] & 0x0F);
        return str;
    }
    
    /*
     * Add integers, wrapping at 2^32
     */
    private int add(int x, int y)
    {
        return ((x & 0x7FFFFFFF) + (y & 0x7FFFFFFF)) ^ (x & 0x80000000) ^ (y & 0x80000000);
    }
    
    /*
     * Bitwise rotate a 32-bit number to the left
     */
    private int rol(int num, int cnt)
    {
        return (num << cnt) | (num >>> (32 - cnt));
    }
    
    /*
     * These functions implement the basic operation for each round of the algorithm.
     */
    private int cmn(int q, int a, int b, int x, int s, int t)
    {
        return add(rol(add(add(a, q), add(x, t)), s), b);
    }
    
    private int ff(int a, int b, int c, int d, int x, int s, int t)
    {
        return cmn((b & c) | ((~b) & d), a, b, x, s, t);
    }
    
    private int gg(int a, int b, int c, int d, int x, int s, int t)
    {
        return cmn((b & d) | (c & (~d)), a, b, x, s, t);
    }
    
    private int hh(int a, int b, int c, int d, int x, int s, int t)
    {
        return cmn(b ^ c ^ d, a, b, x, s, t);
    }
    
    private int ii(int a, int b, int c, int d, int x, int s, int t)
    {
        return cmn(c ^ (b | (~d)), a, b, x, s, t);
    }
    
    /*
     * Convert a byte array to a sequence of 16-word blocks, stored as an array. Append padding bits and the length, as
     * described in the MD5 standard.
     */
    
    private int[] str2blks_MD5(byte[] str)
    {
        int nblk = ((str.length + 8) >> 6) + 1;
        int[] blks = new int[nblk * 16];
        int i = 0;
        for (i = 0; i < nblk * 16; i++)
        {
            blks[i] = 0;
        }
        for (i = 0; i < str.length; i++)
        {
            blks[i >> 2] |= (str[i] & 0xFF) << ((i % 4) * 8);
        }
        blks[i >> 2] |= 0x80 << ((i % 4) * 8);
        blks[nblk * 16 - 2] = str.length * 8;
        
        return blks;
    }
    
    public byte[] calcMD5(byte[] str, byte[] key)
    {
        byte[] mac = new byte[16];
        
        int[] x = str2blks_MD5(str);
        
        /*
         * java initial vector : a = 0x67452301 b = 0xEFCDAB89 c = 0x98BADCFE d = 0x01325476
         */
        
        int a = (((key[3] & 0xFF) << 24) | ((key[2] & 0xFF) << 16) | ((key[1] & 0xFF) << 8) | (key[0] & 0xFF));
        int b = (((key[7] & 0xFF) << 24) | ((key[6] & 0xFF) << 16) | ((key[5] & 0xFF) << 8) | (key[4] & 0xFF));
        int c = (((key[11] & 0xFF) << 24) | ((key[10] & 0xFF) << 16) | ((key[9] & 0xFF) << 8) | (key[8] & 0xFF));
        int d = (((key[15] & 0xFF) << 24) | ((key[14] & 0xFF) << 16) | ((key[13] & 0xFF) << 8) | (key[12] & 0xFF));
        
        for (int i = 0; i < x.length; i += 16)
        {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            
            a = ff(a, b, c, d, x[i + 0], 7, 0xD76AA478);
            d = ff(d, a, b, c, x[i + 1], 12, 0xE8C7B756);
            c = ff(c, d, a, b, x[i + 2], 17, 0x242070DB);
            b = ff(b, c, d, a, x[i + 3], 22, 0xC1BDCEEE);
            a = ff(a, b, c, d, x[i + 4], 7, 0xF57C0FAF);
            d = ff(d, a, b, c, x[i + 5], 12, 0x4787C62A);
            c = ff(c, d, a, b, x[i + 6], 17, 0xA8304613);
            b = ff(b, c, d, a, x[i + 7], 22, 0xFD469501);
            a = ff(a, b, c, d, x[i + 8], 7, 0x698098D8);
            d = ff(d, a, b, c, x[i + 9], 12, 0x8B44F7AF);
            c = ff(c, d, a, b, x[i + 10], 17, 0xFFFF5BB1);
            b = ff(b, c, d, a, x[i + 11], 22, 0x895CD7BE);
            a = ff(a, b, c, d, x[i + 12], 7, 0x6B901122);
            d = ff(d, a, b, c, x[i + 13], 12, 0xFD987193);
            c = ff(c, d, a, b, x[i + 14], 17, 0xA679438E);
            b = ff(b, c, d, a, x[i + 15], 22, 0x49B40821);
            
            a = gg(a, b, c, d, x[i + 1], 5, 0xF61E2562);
            d = gg(d, a, b, c, x[i + 6], 9, 0xC040B340);
            c = gg(c, d, a, b, x[i + 11], 14, 0x265E5A51);
            b = gg(b, c, d, a, x[i + 0], 20, 0xE9B6C7AA);
            a = gg(a, b, c, d, x[i + 5], 5, 0xD62F105D);
            d = gg(d, a, b, c, x[i + 10], 9, 0x02441453);
            c = gg(c, d, a, b, x[i + 15], 14, 0xD8A1E681);
            b = gg(b, c, d, a, x[i + 4], 20, 0xE7D3FBC8);
            a = gg(a, b, c, d, x[i + 9], 5, 0x21E1CDE6);
            d = gg(d, a, b, c, x[i + 14], 9, 0xC33707D6);
            c = gg(c, d, a, b, x[i + 3], 14, 0xF4D50D87);
            b = gg(b, c, d, a, x[i + 8], 20, 0x455A14ED);
            a = gg(a, b, c, d, x[i + 13], 5, 0xA9E3E905);
            d = gg(d, a, b, c, x[i + 2], 9, 0xFCEFA3F8);
            c = gg(c, d, a, b, x[i + 7], 14, 0x676F02D9);
            b = gg(b, c, d, a, x[i + 12], 20, 0x8D2A4C8A);
            
            a = hh(a, b, c, d, x[i + 5], 4, 0xFFFA3942);
            d = hh(d, a, b, c, x[i + 8], 11, 0x8771F681);
            c = hh(c, d, a, b, x[i + 11], 16, 0x6D9D6122);
            b = hh(b, c, d, a, x[i + 14], 23, 0xFDE5380C);
            a = hh(a, b, c, d, x[i + 1], 4, 0xA4BEEA44);
            d = hh(d, a, b, c, x[i + 4], 11, 0x4BDECFA9);
            c = hh(c, d, a, b, x[i + 7], 16, 0xF6BB4B60);
            b = hh(b, c, d, a, x[i + 10], 23, 0xBEBFBC70);
            a = hh(a, b, c, d, x[i + 13], 4, 0x289B7EC6);
            d = hh(d, a, b, c, x[i + 0], 11, 0xEAA127FA);
            c = hh(c, d, a, b, x[i + 3], 16, 0xD4EF3085);
            b = hh(b, c, d, a, x[i + 6], 23, 0x04881D05);
            a = hh(a, b, c, d, x[i + 9], 4, 0xD9D4D039);
            d = hh(d, a, b, c, x[i + 12], 11, 0xE6DB99E5);
            c = hh(c, d, a, b, x[i + 15], 16, 0x1FA27CF8);
            b = hh(b, c, d, a, x[i + 2], 23, 0xC4AC5665);
            
            a = ii(a, b, c, d, x[i + 0], 6, 0xF4292244);
            d = ii(d, a, b, c, x[i + 7], 10, 0x432AFF97);
            c = ii(c, d, a, b, x[i + 14], 15, 0xAB9423A7);
            b = ii(b, c, d, a, x[i + 5], 21, 0xFC93A039);
            a = ii(a, b, c, d, x[i + 12], 6, 0x655B59C3);
            d = ii(d, a, b, c, x[i + 3], 10, 0x8F0CCC92);
            c = ii(c, d, a, b, x[i + 10], 15, 0xFFEFF47D);
            b = ii(b, c, d, a, x[i + 1], 21, 0x85845DD1);
            a = ii(a, b, c, d, x[i + 8], 6, 0x6FA87E4F);
            d = ii(d, a, b, c, x[i + 15], 10, 0xFE2CE6E0);
            c = ii(c, d, a, b, x[i + 6], 15, 0xA3014314);
            b = ii(b, c, d, a, x[i + 13], 21, 0x4E0811A1);
            a = ii(a, b, c, d, x[i + 4], 6, 0xF7537E82);
            d = ii(d, a, b, c, x[i + 11], 10, 0xBD3AF235);
            c = ii(c, d, a, b, x[i + 2], 15, 0x2AD7D2BB);
            b = ii(b, c, d, a, x[i + 9], 21, 0xEB86D391);
            
            a = add(a, olda);
            b = add(b, oldb);
            c = add(c, oldc);
            d = add(d, oldd);
        }
        
        mac[0] = (byte)((a >> 0) & 0xFF);
        mac[1] = (byte)((a >> 8) & 0xFF);
        mac[2] = (byte)((a >> 16) & 0xFF);
        mac[3] = (byte)((a >> 24) & 0xFF);
        mac[4] = (byte)((b >> 0) & 0xFF);
        mac[5] = (byte)((b >> 8) & 0xFF);
        mac[6] = (byte)((b >> 16) & 0xFF);
        mac[7] = (byte)((b >> 24) & 0xFF);
        mac[8] = (byte)((c >> 0) & 0xFF);
        mac[9] = (byte)((c >> 8) & 0xFF);
        mac[10] = (byte)((c >> 16) & 0xFF);
        mac[11] = (byte)((c >> 24) & 0xFF);
        mac[12] = (byte)((d >> 0) & 0xFF);
        mac[13] = (byte)((d >> 8) & 0xFF);
        mac[14] = (byte)((d >> 16) & 0xFF);
        mac[15] = (byte)((d >> 24) & 0xFF);
        
        return mac;
    }
    
    /**
     * 
     * @作者 付天有
     * @编写日期 2013-1-31
     * @功能描述 协商密钥校验密码用
     * @返回值 byte[]
     * @参数
     */
    public byte[] calcMD5(byte[] str)
    {
        return calcMD5(str, key);
    }
    
    public byte[] calcMD5(String str)
    {
        byte[] buffer = DataChange.Unicode2Byte(str);
        return calcMD5(buffer);
    }
    
    /*
     * byteHEX(),用来把一个byte类型的数转换成十六进制的ASCII表示, 　因为java中的byte的toString无法实现这一点,我们又没有C语言中的 sprintf(outbuf,"%02X",ib)
     */
    public static String byteHEX(byte ib)
    {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
    
    /**
     * 
     * @作者 付天有
     * @编写日期 2013-3-18
     * @功能描述 获得一个字节数组的子集
     * @返回值 指定长度的字节数组,若不够指定长度 高位(后)补零
     * @参数
     */
    public byte[] getSubBytes(byte[] original, int newLength)
    {
        
        if (original.length >= newLength)
        {
            return Arrays.copyOf(original, newLength);
        }
        else
        {
            byte[] newBytes = new byte[newLength];
            for (int i = 0; i < original.length; i++)
            {
                newBytes[newLength - original.length + i] = original[i];
            }
            return newBytes;
        }
    }
    
    /**
     * 
     * @作者 付天有
     * @编写日期 2013-3-20
     * @功能描述 通用加密字符串
     * 
     * @返回值 byte[]
     * @参数
     */
    public String encrypt(String str)
    {
        return hex(calcMD5(str));
    }
    
    public String encrypt(byte[] b)
    {
        return hex(calcMD5(b));
    }
    
}
