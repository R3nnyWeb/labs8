module blok_ron(c,wreg,d_bus,k, x,y);
input c,wreg;
input [7:0] d_bus;
input [15:0] k;
output [7:0] x,y;
reg [7:0] x,y;
reg [7:0] ron [15:0];
always begin
  if (k[15:12] <= 4'ha) begin
	x <= ron[k[11:8]];
	y <= (k[15]==0 | k[15:12]==9) ? 8'h00 : ron[k[7:4]];
  end
  else if (k[15:12]==4'hb) begin
	x <= ron[k[8:6]];
	y <= ron[k[5:3]];
  end
  else if (k[15:12]==4'hc | k[15:12]==4'hd) begin
	x <= ron[k[11:9]];
	y <= ron[k[8:6]];
  end
  else begin
	x <= 0;
	y <= 0;
  end
end
always @(posedge c)
  if (wreg) begin
	if (k[15:12]==4'hb | k[15:12]==4'hc | k[15:12]==4'hd) ron[k[11:9]] = d_bus;
	else ron[k[11:8]] = d_bus;
  end
endmodule