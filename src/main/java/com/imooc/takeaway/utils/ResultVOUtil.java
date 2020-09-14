package com.imooc.takeaway.utils;

import com.imooc.takeaway.viewObject.ResultVO;

public class ResultVOUtil {
  public static ResultVO success(Object object) {
    ResultVO resultVO = new ResultVO();
    resultVO.setCode(0);
    resultVO.setMessage("success");
    resultVO.setData(object);

    return resultVO;
  }

  public static ResultVO success() {
    return success(null);
  }

  public static ResultVO error(Integer code, String msg) {
    ResultVO resultVO = new ResultVO();
    resultVO.setCode(code);
    resultVO.setMessage(msg);

    return resultVO;
  }
}
