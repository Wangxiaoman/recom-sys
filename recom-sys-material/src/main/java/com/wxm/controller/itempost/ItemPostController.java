package com.wxm.controller.itempost;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recom/material")
public class ItemPostController {

//	// 上报物料接口
//	@PostMapping(value = "/items")
//	@CrossOrigin
//	public ResultJson saveItems(HttpServletRequest request, @RequestBody(required = true) List<Item> items,
//			@RequestParam(value = "source", required = true, defaultValue = "1") int source,
//			@RequestParam(value = "accessToken", required = true) String accessToken) {
//		String ip = HttpTools.getRemoteHost(request)[0];
//		if (CollectionUtils.isEmpty(items)) {
//			return new ResultJson(CommonStatus.ITEM_NOT_EXIST);
//		}
//		if (items.size() > Constants.ITEM_BATCH_MAX_NUMBER) {
//			return new ResultJson(CommonStatus.ITEM_BATCH_TOO_MUCH);
//		}
//		ItemSet itemSet = cacheService.getItemSetByAccessToken(accessToken);
//		if (itemSet == null || Objects.equal(itemSet.getStatus(), 0)) {
//			return new ResultJson(CommonStatus.ITEM_SET_NOT_EXIST);
//		}
//		JSONArray ja = itemPostService.checkItems(items, itemSet.getId());
//        if (ja != null && ja.size() > 0) {
//            return new ResultJson(CommonStatus.ITEM_URL_ERROR, ja);
//		}
//
//        for (Item item : items) {
//            if (StringUtils.isBlank(item.getUrl())) {
//                return new ResultJson(CommonStatus.ITEM_URL_MIS);
//            }
//            if(StringUtils.isBlank(item.getTitle())){
//                return new ResultJson(CommonStatus.ITEM_TITLE_EMPTY);
//            }
//            if (StringUtils.isBlank(item.getItemId())) {
//                if (Objects.equal(source, Constants.ItemUploadType.API.ordinal())) {
//                    return new ResultJson(CommonStatus.ITEM_ID_MIS);
//                } else {
//                    item.setItemId(MD5.getMD5CodeWithSalt(item.getUrl()));
//                }
//            }
//        }
//		itemPostService.batchItems(items, itemSet, source, ip);
//		return new ResultJson(CommonStatus.SUCCESS);
//	}
//	
//    // 上报物料接口
//    @PostMapping(value = "/items/remove")
//    @CrossOrigin
//    public ResultJson removeItems(HttpServletRequest request, @RequestBody(required = true) Set<String> itemIds,
//            @RequestParam(required = false, defaultValue = "1", value = "type") int type,
//            @RequestParam(value = "accessToken", required = true) String accessToken) {
//        if(CollectionUtils.isEmpty(itemIds)){
//            return new ResultJson(CommonStatus.ITEM_KEY_EMPTY);
//        }
//        if(itemIds.size() > Constants.ITEM_BATCH_MAX_NUMBER){
//            return new ResultJson(CommonStatus.ITEM_BATCH_TOO_MUCH);
//        }
//        ItemSet itemSet = cacheService.getItemSetByAccessToken(accessToken);
//        if (itemSet == null || Objects.equal(itemSet.getStatus(), 0)) {
//            return new ResultJson(CommonStatus.ITEM_SET_NOT_EXIST);
//        }
//        String ip = HttpTools.getRemoteHost(request)[0];
//        itemPostService.removeItems(itemIds, type, itemSet, ip);
//        return new ResultJson(CommonStatus.SUCCESS);
//    }
//
//	// 物料查询接口
//	@PostMapping(value = "/items/search")
//	@CrossOrigin
//	public ResultJson searchItems(HttpServletRequest request, @RequestBody(required = true) Set<String> itemIds,
//	        @RequestParam(required = false, defaultValue = "1", value = "type") int type,
//	        @RequestParam(required = false, defaultValue = "0", value = "extra") int extra,
//	        @RequestParam(value = "accessToken", required = true) String accessToken) {
//		if (CollectionUtils.isEmpty(itemIds)) {
//			return new ResultJson(CommonStatus.ITEM_KEY_EMPTY);
//		}
//		if (itemIds.size() > Constants.ITEM_BATCH_MAX_NUMBER) {
//			return new ResultJson(CommonStatus.ITEM_BATCH_TOO_MUCH);
//		}
//		ItemSet itemSet = cacheService.getItemSetByAccessToken(accessToken);
//		if (itemSet == null || Objects.equal(itemSet.getStatus(), 0)) {
//			return new ResultJson(CommonStatus.ITEM_SET_NOT_EXIST);
//		}
//		String ip = HttpTools.getRemoteHost(request)[0];
//		
//        if (Objects.equal(1, extra)) {
//            List<ItemVOExtra> itemExtras = itemPostService.searchExtraItems(itemIds, itemSet, ip, type);
//            return new ResultJson(CommonStatus.SUCCESS, itemExtras);
//        } else {
//            List<ItemVO> items = itemPostService.searchItems(itemIds, itemSet, ip, type);
//            return new ResultJson(CommonStatus.SUCCESS, items);
//        }
//	}
}
