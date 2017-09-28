function checkCodeChar(str)//True 没有全角，False有全角
{
    for (var i = 0; i < str.length; i++)
    {
        strCode = str.charCodeAt(i);
        if ((strCode > 65248) || (strCode == 12288))
        {
            return false;
        }
    }
    return true;
}