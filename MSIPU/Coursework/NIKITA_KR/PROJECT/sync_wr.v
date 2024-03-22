module sync_wr (k, wreg, wmem);
input [15:0] k ;
output wreg, wmem ;
assign wreg = ~k[15] |( k[15] & ~k[14] & ~k[13] );
assign wmem = k[15] & ~k[14] & k[13] &~ k[12]& k[0];
endmodule